package com.hp.kalexa.core.handler

import com.hp.kalexa.core.annotation.*
import com.hp.kalexa.core.handler.SpeechHandler.Companion.INTENT_CONTEXT
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.IntentExecutor
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.core.util.IntentUtil.defaultGreetings
import com.hp.kalexa.core.util.IntentUtil.unsupportedIntent
import com.hp.kalexa.core.util.Util.findAnnotatedClasses
import com.hp.kalexa.core.util.Util.getIntentPackage
import com.hp.kalexa.core.util.Util.loadIntentClassesFromPackage
import com.hp.kalexa.model.extension.attribute
import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.alexaResponse
import com.sun.xml.internal.txw2.IllegalAnnotationException
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses


open class DefaultSpeechHandler : SpeechHandler {

    private val intentClasses by lazy { loadIntentClasses() }
    private val intentInstances = mutableMapOf<String, IntentExecutor>()

    override fun handleSessionStartedRequest(envelope: AlexaRequestEnvelope<SessionStartedRequest>) = AlexaResponse.emptyResponse()

    override fun handleLaunchRequest(envelope: AlexaRequestEnvelope<LaunchRequest>): AlexaResponse {
        println("=========================== LaunchRequest =========================")
        println("Looking for Launcher intents in ${getIntentPackage()}")
        val launcherClasses = findAnnotatedClasses(intentClasses, Launcher::class)
        val uniqueValues = launcherClasses.values.toHashSet()
        println("Detected ${uniqueValues.size} intent classes with Launcher annotation.")
        return when {
            uniqueValues.isEmpty() -> defaultGreetings()
            uniqueValues.size > 1 -> illegalAnnotationArgument("Launcher")
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
            intentName == BuiltInIntent.FALLBACK_INTENT.rawValue -> fallbackIntent(envelope)
            intentName == builtInIntent.rawValue -> unknownIntentContext(builtInIntent, envelope)
            else -> builtInIntent(intentName, builtInIntent, envelope)
        }
    }

    private fun fallbackIntent(envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        val fallbackClasses = findAnnotatedClasses(intentClasses, Fallback::class)
        val uniqueValues = fallbackClasses.values.toHashSet()
        println("Detected ${uniqueValues.size} intent classes with Fallback annotation.")
        return when {
            uniqueValues.isEmpty() -> unsupportedIntent()
            uniqueValues.size > 1 -> illegalAnnotationArgument("Fallback")
            else -> {
                val entry = fallbackClasses.entries.first()
                println("Class with Fallback annotation: ${entry.value}")
                getIntentExecutorOf(entry.key, envelope)!!.onFallbackIntent(envelope.request)
            }
        }
    }

    private fun customIntent(intentName: String, envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        val intentExecutor = getIntentExecutorOf(intentName, envelope)
        return intentExecutor?.let { executor ->
            val alexaResponse = executor.onIntentRequest(envelope.request)
            generateResponse(executor, alexaResponse)
        } ?: unknownIntentException(intentName)
    }

    private fun unknownIntentContext(builtInIntent: BuiltInIntent, envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        val recoverIntentContextClasses = findAnnotatedClasses(intentClasses, RecoverIntentContext::class)
        val uniqueClasses = recoverIntentContextClasses.values.toHashSet()
        return when {
            uniqueClasses.isEmpty() -> IntentUtil.defaultBuiltInResponse(builtInIntent, envelope.session.attributes)
            uniqueClasses.size > 1 -> illegalAnnotationArgument("RecoverIntentContext")
            else -> {
                val entry = recoverIntentContextClasses.entries.first()
                println("Class with RecoverIntentContext annotation: ${entry.value}")
                getIntentExecutorOf(entry.key, envelope)!!.onUnknownIntentContext(builtInIntent)
            }
        }
    }

    private fun builtInIntent(intentName: String, builtInIntent: BuiltInIntent, envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        val intentExecutor = getIntentExecutorOf(intentName, envelope)
        return intentExecutor?.let { executor ->
            val alexaResponse = executor.onBuiltInIntent(builtInIntent, envelope.request)
            generateResponse(executor, alexaResponse)
        } ?: unknownIntentException(intentName)
    }


    override fun handleElementSelectedRequest(envelope: AlexaRequestEnvelope<ElementSelectedRequest>): AlexaResponse {
        println("=========================== ElementSelectedRequest =========================")
        val intentName = envelope.session.attribute<String>(INTENT_CONTEXT)
                ?: envelope.request.token.split("\\|").first()
        val intentExecutor = getIntentExecutorOf(intentName, envelope)
        return intentExecutor?.let {
            val alexaResponse = it.onElementSelected(envelope.request)
            generateResponse(it, alexaResponse)
        } ?: unknownIntentException(intentName)
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

    override fun handleConnectionsResponseRequest(envelope: AlexaRequestEnvelope<ConnectionsResponseRequest>): AlexaResponse {
        println("=========================== Connections.Response =========================")
        val intent = envelope.request.token.split("\\|").first()
        val intentExecutor = getIntentExecutorOf(intent, envelope)
        return intentExecutor?.let {
            val alexaResponse = it.onConnectionsResponse(envelope.request)
            generateResponse(it, alexaResponse)
        } ?: unknownIntentException(intent)
    }

    override fun handleConnectionsRequest(envelope: AlexaRequestEnvelope<ConnectionsRequest>): AlexaResponse {
        println("=========================== ConnectionsRequest =========================")
        val fullfillerClasses = findAnnotatedClasses(intentClasses, Fullfiller::class)
        val uniqueValues = fullfillerClasses.values.toHashSet()
        println("Detected ${uniqueValues.size} intent classes with Fulfiller annotation.")
        return when {
            uniqueValues.isEmpty() -> unsupportedIntent()
            uniqueValues.size > 1 -> illegalAnnotationArgument("Fullfiller")
            else -> {
                val entry = fullfillerClasses.entries.first()
                println("Class with Fallback annotation: ${entry.value}")
                getIntentExecutorOf(entry.key, envelope)!!.onConnectionsRequest(envelope.request)
            }
        }
    }

    private fun generateResponse(executor: IntentExecutor, alexaResponse: AlexaResponse): AlexaResponse {
        return if (executor.isIntentContextLocked() && alexaResponse.sessionAttributes[INTENT_CONTEXT] == null) {
            alexaResponse.copy(sessionAttributes = alexaResponse.sessionAttributes + Pair(INTENT_CONTEXT, executor::class.java.simpleName))
        } else {
            alexaResponse
        }
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
        val intentClasses = loadIntentClassesFromPackage()
                .filter { it.superclasses.find { it.simpleName == IntentExecutor::class.java.simpleName } != null }
                .associate { it.simpleName!! to it as KClass<out IntentExecutor> }

        val intentNamesList = lookupIntentNamesFromIntentsAnnotation(intentClasses)
        return intentClasses + intentNamesList
    }

    /**
     * Look @Intents annotation up
     * @param intentClasses to look intent names up
     * @return List of Pair objects with IntentName as key and class that owns the annotation as value.
     */
    private fun lookupIntentNamesFromIntentsAnnotation(intentClasses: Map<String, KClass<out IntentExecutor>>):
            List<Pair<String, KClass<out IntentExecutor>>> {
        return findAnnotatedClasses(intentClasses, Intents::class)
                .map { (_, value) ->
                    val intents = value.findAnnotation<Intents>()!!
                    intents.intentNames.map { it to value }
                }.flatten()
    }

    /**
     * Retrieves an instance of a given intentName, if no such instance exists, it will be created, put into the hash
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