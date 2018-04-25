package com.hp.kalexa.core.handler

import com.google.common.reflect.ClassPath
import com.hp.kalexa.model.*
import com.hp.kalexa.model.request.AlexaRequestEnvelope
import com.hp.kalexa.core.annotation.Helper
import com.hp.kalexa.core.annotation.Launcher
import com.hp.kalexa.core.handler.SpeechHandler.Companion.INTENT_CONTEXT
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.IntentExecutor
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.core.util.IntentUtil.defaultGreetings
import com.hp.kalexa.core.util.IntentUtil.retryIntent
import com.hp.kalexa.core.util.IntentUtil.unsupportedIntent
import com.hp.kalexa.core.util.Util.findAnnotatedMethod
import com.hp.kalexa.core.util.Util.getIntentPackage
import com.hp.kalexa.model.extension.attribute
import com.sun.xml.internal.txw2.IllegalAnnotationException
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.superclasses

open class DefaultSpeechHandler : SpeechHandler {

    private val intentClasses by lazy { loadIntentClasses() }
    private val intentInstances = mutableMapOf<String, IntentExecutor>()

    override fun handleSessionStartedRequest(envelope: AlexaRequestEnvelope<SessionStartedRequest>) = AlexaResponse.emptyResponse()

    override fun handleLaunchRequest(envelope: AlexaRequestEnvelope<LaunchRequest>): AlexaResponse {
        println("=========================== LaunchRequest =========================")
        println("Looking for Launcher intents in ${getIntentPackage()}")
        val launcherClasses = findAnnotatedMethod(intentClasses, Launcher::class, "onLaunchIntent")
        println("Detected ${launcherClasses.size} intent classes with Launcher annotation.")
        return when {
            launcherClasses.isEmpty() -> defaultGreetings()
            launcherClasses.size > 1 -> illegalAnnotationArgument("Launcher")
            else -> {
                val entry = launcherClasses.entries.first()
                println("Class with Launcher annotation: ${entry.value}")
                getIntentExecutorOf(entry.key, envelope)!!.onLaunchIntent(envelope.request)
            }
        }
    }

    override fun handleIntentRequest(envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        println("=========================== IntentRequest =========================")
        val intentName = envelope.session.attribute<String>(INTENT_CONTEXT) ?: envelope.request.intent.name
        val builtInIntent = BuiltInIntent.getBuiltInIntent(envelope.request.intent.name)
        println("Intent name: $intentName - Built in Intent: $builtInIntent")
        return when {
            builtInIntent == null -> customIntent(intentName, envelope)
            intentName == builtInIntent.rawValue -> unknownIntent(builtInIntent, envelope)
            else -> builtInIntent(intentName, builtInIntent, envelope)
        }
    }

    private fun customIntent(intentName: String, envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        return getIntentExecutorOf(intentName, envelope)?.onIntentRequest(envelope.request)
                ?: unknownIntentException(intentName)
    }

    private fun unknownIntent(builtInIntent: BuiltInIntent, envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        val helperClasses = findAnnotatedMethod(intentClasses, Helper::class, "onUnknownIntent")
        return when {
            helperClasses.isEmpty() -> IntentUtil.defaultBuiltInResponse(builtInIntent, envelope.session.attributes)
            helperClasses.size > 1 -> illegalAnnotationArgument("Helper")
            else -> {
                val entry = helperClasses.entries.first()
                println("Class with Helper annotation: ${entry.value}")
                getIntentExecutorOf(entry.key, envelope)!!.onUnknownIntent(builtInIntent)
            }
        }
    }

    private fun builtInIntent(intentName: String, builtInIntent: BuiltInIntent, envelope: AlexaRequestEnvelope<IntentRequest>) =
            getIntentExecutorOf(intentName, envelope)?.onBuiltInIntent(builtInIntent, envelope.request)
                    ?: unknownIntentException(intentName)

    override fun handleElementSelectedRequest(envelope: AlexaRequestEnvelope<ElementSelectedRequest>): AlexaResponse {
        println("=========================== ElementSelectedRequest =========================")
        val intentName = envelope.session.attribute<String>(INTENT_CONTEXT)
        return intentName?.let {
            getIntentExecutorOf(intentName, envelope)?.onElementSelected(envelope.request)
                    ?: retryIntent(envelope.session.attributes)
        } ?: unsupportedIntent()
    }

    override fun handleSessionEndedRequest(envelope: AlexaRequestEnvelope<SessionEndedRequest>): AlexaResponse {
        return if (envelope.request.error != null && envelope.request.reason != null) {
            alexaResponse {
                response {
                    shouldEndSession = true
                    speech {
                        envelope.request.error?.type?.name ?: ""
                    }
                    simpleCard {
                        title = envelope.request.reason?.name ?: ""
                        content = envelope.request.error?.message ?: ""
                    }
                }
            }
        } else {
            AlexaResponse.emptyResponse()
        }
    }

    override fun handleLinkResultRequest(envelope: AlexaRequestEnvelope<LinkResultRequest>): AlexaResponse {
        println("=========================== Links.LinkResult =========================")
        val intent = envelope.request.token.split("\\|").first()
        return getIntentExecutorOf(intent, envelope)?.onLinkResult(envelope.request)
                ?: unsupportedIntent()
    }

    private fun unknownIntentException(intentName: String): AlexaResponse {
        throw IllegalArgumentException("It was not possible to map intent $intentName to a Class. " +
                "Please check the name of the intent and package location")
    }

    private fun illegalAnnotationArgument(annotation: String): AlexaResponse {
        throw IllegalAnnotationException("The skill can only have one @$annotation method.")
    }

    @Suppress("unchecked_cast")
    private fun loadIntentClasses(): Map<String, KClass<out IntentExecutor>> {
        val classes = ClassPath.from(Thread.currentThread().contextClassLoader).getTopLevelClasses(getIntentPackage())
        return classes.map { it.load().kotlin }
                .filter { it.superclasses.find { it.simpleName == IntentExecutor::class.java.simpleName } != null }
                .associate { it.simpleName!! to it as KClass<out IntentExecutor> }
    }

    /**
     * Retrieves an instance of a given intentName, if no instance is created, it will create, put into the hash
     * and return it
     * @param intentName
     * @return an instance of the intentName
     */
    private fun getIntentExecutorOf(intentName: String, envelope: AlexaRequestEnvelope<*>): IntentExecutor? {
        return intentClasses[intentName]?.let {
            val intentExecutor: IntentExecutor = intentInstances.getOrPut(intentName) { it.createInstance() }
            intentExecutor.sessionAttributes = envelope.session.attributes
            intentExecutor.session = envelope.session
            intentExecutor.context = envelope.context
            intentExecutor.version = envelope.version
            intentExecutor
        }
    }

}