/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.core.annotation.CanFulfillIntent
import com.hp.kalexa.core.annotation.Intent
import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.extension.hasSuperClassOf
import com.hp.kalexa.core.handler.BaseHandlerRepository
import com.hp.kalexa.core.handler.requesthandler.BasicHandler.Companion.INTENT_CONTEXT
import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.CanFulfillIntentHandler
import com.hp.kalexa.core.intent.CancelIntentHandler
import com.hp.kalexa.core.intent.FallbackIntentHandler
import com.hp.kalexa.core.intent.HelpIntentHandler
import com.hp.kalexa.core.intent.IntentHandler
import com.hp.kalexa.core.intent.LaunchRequestHandler
import com.hp.kalexa.core.intent.RecoverIntentContextHandler
import com.hp.kalexa.core.intent.RequesterHandler
import com.hp.kalexa.core.intent.StopIntentHandler
import com.hp.kalexa.core.util.IntentUtil.defaultBuiltInResponse
import com.hp.kalexa.core.util.IntentUtil.defaultGreetings
import com.hp.kalexa.core.util.IntentUtil.helpIntent
import com.hp.kalexa.core.util.IntentUtil.unsupportedIntent
import com.hp.kalexa.core.util.Util.getScanPackage
import com.hp.kalexa.model.extension.attribute
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.BaseIntentRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.SessionEndedRequest
import com.hp.kalexa.model.request.SessionResumedRequest
import com.hp.kalexa.model.request.SessionStartedRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.dsl.alexaResponse
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation

open class DefaultBasicHandler(repository: BaseHandlerRepository) : BasicHandler, AbstractRequestHandler(repository) {
    private val logger = LogManager.getLogger(DefaultBasicHandler::class.java)

    private val intentMap: Map<Set<String>, KClass<out BaseHandler>> = mapIntentHandlersOf<Intent>(IntentHandler::class)
    private val canFulfillMap: Map<Set<String>, KClass<out BaseHandler>> = mapIntentHandlersOf<CanFulfillIntent>(
        CanFulfillIntentHandler::class
    )

    override fun handleSessionStartedRequest(alexaRequest: AlexaRequest<SessionStartedRequest>) =
        AlexaResponse.emptyResponse()

    override fun handleLaunchRequest(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse {
        logger.info("=========================== LaunchRequest =========================")
        logger.debug("Looking for LaunchIntent intents in ${getScanPackage()}")
        val launchRequest: LaunchRequestHandler? = getHandler(LaunchRequestHandler::class)?.cast()

        return launchRequest?.let {
            val alexaResponse = launchRequest.onLaunchIntent(alexaRequest)
            return generateResponse(launchRequest, alexaRequest, alexaResponse)
        } ?: defaultGreetings()
    }

    override fun handleIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        logger.info("=========================== IntentRequest =========================")
        val intentName = alexaRequest.session?.attribute<String>(INTENT_CONTEXT) ?: alexaRequest.request.intent.name
        val builtInIntent = BuiltInIntent.getBuiltInIntent(alexaRequest.request.intent.name)
        logger.debug("Intent name: $intentName - Built in Intent: $builtInIntent")
        return when {
            builtInIntent == null -> customIntent(intentName, alexaRequest)
            intentName == BuiltInIntent.FALLBACK_INTENT.rawValue -> fallbackIntent(alexaRequest)
            intentName == BuiltInIntent.HELP_INTENT.rawValue -> helpIntent(alexaRequest)
            intentName == BuiltInIntent.CANCEL_INTENT.rawValue -> cancelIntent(alexaRequest)
            intentName == BuiltInIntent.STOP_INTENT.rawValue -> stopIntent(alexaRequest)
            intentName == builtInIntent.rawValue -> unknownIntentContext(builtInIntent, alexaRequest)
            else -> builtInIntent(intentName, builtInIntent, alexaRequest)
        }
    }

    override fun handleCanFulfillIntentRequest(alexaRequest: AlexaRequest<CanFulfillIntentRequest>): AlexaResponse {
        logger.info("=========================== CanFulfillIntentRequest =========================")
        return handleBaseIntentRequest(
            alexaRequest,
            canFulfillMap
        ) { intentHandler -> (intentHandler as CanFulfillIntentHandler).onCanFulfillIntent(alexaRequest) }
    }

    override fun handleElementSelectedRequest(alexaRequest: AlexaRequest<ElementSelectedRequest>): AlexaResponse {
        logger.info("=========================== ElementSelectedRequest =========================")
        val intentName = alexaRequest.session?.attribute<String>(INTENT_CONTEXT)
            ?: alexaRequest.request.token.split("\\|".toRegex()).first()
        val intentHandler = getIntentHandlerOf(intentName, intentMap)
        return intentHandler?.let {
            val alexaResponse = it.onElementSelected(alexaRequest)
            generateResponse(it, alexaRequest, alexaResponse)
        } ?: unknownIntentException(intentName)
    }

    override fun handleSessionEndedRequest(alexaRequest: AlexaRequest<SessionEndedRequest>): AlexaResponse {
        logger.info("=========================== SessionEndedRequest =========================")
        return if (alexaRequest.request.error != null && alexaRequest.request.reason != null) {
            alexaResponse {
                response {
                    shouldEndSession = true
                    speech {
                        alexaRequest.request.error?.type?.name ?: ""
                    }
                    simpleCard {
                        title = alexaRequest.request.reason?.name ?: ""
                        content = alexaRequest.request.error?.message ?: ""
                    }
                }
            }
        } else {
            AlexaResponse.emptyResponse()
        }
    }

    override fun handleSessionResumedRequest(
        alexaRequest: AlexaRequest<SessionResumedRequest>
    ): AlexaResponse {
        logger.info("=========================== SessionResumedRequest =========================")
        val requesterHandler: RequesterHandler? = getHandler(RequesterHandler::class)?.cast()

        return requesterHandler?.let {
            val alexaResponse = requesterHandler.onSessionResumedRequest(alexaRequest)
            return generateResponse(requesterHandler, alexaRequest, alexaResponse)
        } ?: AlexaResponse.emptyResponse()
    }

    private fun handleBaseIntentRequest(
        alexaRequest: AlexaRequest<BaseIntentRequest>,
        classes: Map<Set<String>, KClass<out BaseHandler>> = intentMap,
        intentName: String? = null,
        callback: (BaseHandler) -> AlexaResponse
    ): AlexaResponse {
        val name = intentName ?: alexaRequest.request.intent.name
        val intentHandler = getIntentHandlerOf(name, classes)
        return intentHandler?.let { handler ->
            generateResponse(handler, alexaRequest, callback(handler))
        } ?: unknownIntentException(name)
    }

    private fun customIntent(intentName: String, alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return handleBaseIntentRequest(alexaRequest, intentName = intentName) { intentHandler ->
            (intentHandler as IntentHandler).onIntentRequest(alexaRequest)
        }
    }

    private fun fallbackIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        logger.info("=========================== Fallback Intent =========================")
        val fallback: FallbackIntentHandler? = getHandler(FallbackIntentHandler::class)?.cast()

        return fallback?.let {
            val alexaResponse = fallback.onFallbackIntent(alexaRequest)
            return generateResponse(fallback, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    private fun helpIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        logger.info("=========================== Help Intent =========================")
        val helpHandler: HelpIntentHandler? = getHandler(HelpIntentHandler::class)?.cast()

        return helpHandler?.let {
            val alexaResponse = helpHandler.onHelpIntent(alexaRequest)
            return generateResponse(helpHandler, alexaRequest, alexaResponse)
        } ?: helpIntent()
    }

    private fun cancelIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        logger.info("=========================== Cancel Intent =========================")
        val cancelHandler: CancelIntentHandler? = getHandler(CancelIntentHandler::class)?.cast()

        return cancelHandler?.let {
            val alexaResponse = cancelHandler.onCancelIntent(alexaRequest)
            return generateResponse(cancelHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    private fun stopIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        logger.info("=========================== Stop Intent =========================")
        val stopHandler: StopIntentHandler? = getHandler(StopIntentHandler::class)?.cast()

        return stopHandler?.let {
            val alexaResponse = stopHandler.onStopIntent(alexaRequest)
            return generateResponse(stopHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    private fun unknownIntentContext(
        builtInIntent: BuiltInIntent,
        alexaRequest: AlexaRequest<IntentRequest>
    ): AlexaResponse {
        val recoverIntent: RecoverIntentContextHandler? = getHandler(RecoverIntentContextHandler::class)?.cast()

        return recoverIntent?.let {
            val alexaResponse = recoverIntent.onUnknownIntentContext(builtInIntent)
            return generateResponse(recoverIntent, alexaRequest, alexaResponse)
        } ?: defaultBuiltInResponse(builtInIntent, alexaRequest.session?.attributes)
    }

    /**
     * Executes builtInIntent callback method from the Intent Handler instance from the given intent name.
     * First it looks in the IntentClasses, if the class is not found then it looks in the intent handler instances.
     * This way every built in method works without the need of annotating a class as @Intent if the class is annotated
     * with @LaunchIntent or any other type.
     * @param intentName Intent to have the onBuiltInIntent method executed
     * @param builtInIntent the BuiltInIntent itself
     */
    private fun builtInIntent(
        intentName: String,
        builtInIntent: BuiltInIntent,
        alexaRequest: AlexaRequest<IntentRequest>
    ): AlexaResponse {
        val intentHandler = getIntentHandlerOf(intentName, intentMap) ?: run {
            repository.handlerInstances.keys
                .find { it == intentName }
                ?.let { repository.handlerInstances[intentName] }
                ?.let { getIntentHandlerOf(it::class) }
                ?: run { return unknownIntentException(intentName) }
        }
        val alexaResponse = intentHandler.onBuiltInIntent(builtInIntent, alexaRequest)
        return generateResponse(intentHandler, alexaRequest, alexaResponse)
    }

    /**
     * Throws a IllegalArgumentException
     */
    private fun unknownIntentException(intentName: String): AlexaResponse {
        throw IllegalArgumentException(
            "It was not possible to map intent $intentName to a Class. " +
                "Please make sure that the class implements the correct interface handler or check intent package " +
                "location"
        )
    }

    /**
     * Look a given annotation up
     * @return Map of Kclasses. The Array of mapsTo corresponds to the key and kClass is the value
     */
    private inline fun <reified T : Annotation> mapIntentHandlersOf(kClass: KClass<out BaseHandler>):
        Map<Set<String>, KClass<out BaseHandler>> {
        return repository.handlerClasses.filter {
            it.simpleName == kClass.simpleName || it.hasSuperClassOf(kClass)
        }.map { clazz ->
            val annotation = clazz.declaredFunctions.find {
                it.findAnnotation<T>() != null
            }?.findAnnotation<T>()

            val mapsTo = when (annotation) {
                is Intent -> annotation.cast<Intent>().mapsTo.toSet()
                is CanFulfillIntent -> annotation.cast<CanFulfillIntent>().intents.toSet()
                else -> emptySet()
            } + clazz.simpleName!!
            mapsTo to clazz
        }.toMap()
    }
}
