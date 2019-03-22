/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.annotation.CanFulfillIntent
import com.hp.kalexa.core.annotation.Requester
import com.hp.kalexa.core.annotation.FallbackIntent
import com.hp.kalexa.core.annotation.Provider
import com.hp.kalexa.core.annotation.HelpIntent
import com.hp.kalexa.core.annotation.Intent
import com.hp.kalexa.core.annotation.LaunchIntent
import com.hp.kalexa.core.annotation.ListEvents
import com.hp.kalexa.core.annotation.RecoverIntentContext
import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.handler.SpeechHandler.Companion.INTENT_CONTEXT
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.IntentHandler
import com.hp.kalexa.core.util.IntentUtil.defaultBuiltInResponse
import com.hp.kalexa.core.util.IntentUtil.defaultGreetings
import com.hp.kalexa.core.util.IntentUtil.helpIntent
import com.hp.kalexa.core.util.IntentUtil.unsupportedIntent
import com.hp.kalexa.core.util.Util.findAnnotatedClasses
import com.hp.kalexa.core.util.Util.getIntentPackage
import com.hp.kalexa.core.util.Util.loadIntentClassesFromPackage
import com.hp.kalexa.model.exception.IllegalAnnotationException
import com.hp.kalexa.model.extension.attribute
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.request.ConnectionsRequest
import com.hp.kalexa.model.request.ConnectionsResponseRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.SessionEndedRequest
import com.hp.kalexa.model.request.SessionStartedRequest
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.alexaResponse
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses

open class DefaultSpeechHandler : SpeechHandler {
    private val logger = LogManager.getLogger(DefaultSpeechHandler::class.java)

    private val intentHandlerClasses: List<KClass<out IntentHandler>> = loadIntentHandlerClasses()
    private val intentClasses: Map<Set<String>, KClass<out IntentHandler>> = mapClassesWithIntentAnnotation()
    private val intentHandlerInstances = mutableMapOf<KClass<out Any>, IntentHandler>()

    override fun handleSessionStartedRequest(alexaRequest: AlexaRequest<SessionStartedRequest>) =
        AlexaResponse.emptyResponse()

    override fun handleLaunchRequest(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse {
        logger.info("=========================== LaunchRequest =========================")
        logger.debug("Looking for LaunchIntent intents in ${getIntentPackage()}")
        return intentHandlerInstances[LaunchIntent::class]?.onLaunchIntent(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<LaunchIntent> { result ->
                    when (result) {
                        is Result.Content -> {
                            val alexaResponse = result.intentHandler.onLaunchIntent(alexaRequest)
                            generateResponse(result.intentHandler, alexaRequest, alexaResponse)
                        }
                        is Result.None -> defaultGreetings()
                        is Result.Error -> throw result.exception
                    }
                }
            }
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
            intentName == builtInIntent.rawValue -> unknownIntentContext(builtInIntent, alexaRequest)
            else -> builtInIntent(intentName, builtInIntent, alexaRequest)
        }
    }

    private fun customIntent(intentName: String, alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        val intentHandler = getIntentHandlerOf(intentName)
        return intentHandler?.let { handler ->
            val alexaResponse = handler.onIntentRequest(alexaRequest)
            generateResponse(handler, alexaRequest, alexaResponse)
        } ?: unknownIntentException(intentName)
    }

    private fun fallbackIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        logger.info("=========================== Fallback Intent =========================")
        return intentHandlerInstances[FallbackIntent::class]?.onFallbackIntent(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<FallbackIntent> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onFallbackIntent(alexaRequest)
                        is Result.None -> unsupportedIntent()
                        is Result.Error -> throw result.exception
                    }
                }
            }
    }

    private fun helpIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        logger.info("=========================== Help Intent =========================")
        return intentHandlerInstances[HelpIntent::class]?.onHelpIntent(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<HelpIntent> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onHelpIntent(alexaRequest)
                        is Result.None -> helpIntent()
                        is Result.Error -> throw result.exception
                    }
                }
            }
    }

    private fun unknownIntentContext(
        builtInIntent: BuiltInIntent,
        alexaRequest: AlexaRequest<IntentRequest>
    ): AlexaResponse {
        return intentHandlerInstances[RecoverIntentContext::class]?.onUnknownIntentContext(builtInIntent)
            ?: run {
                return lookupIntentHandlerFromAnnotation<RecoverIntentContext> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onUnknownIntentContext(builtInIntent)
                        is Result.None -> defaultBuiltInResponse(builtInIntent, alexaRequest.session?.attributes)
                        is Result.Error -> throw result.exception
                    }
                }
            }
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
        val intentHandler = getIntentHandlerOf(intentName) ?: run {
            intentHandlerInstances.keys
                .find { it.simpleName == intentName }
                ?.cast<KClass<out IntentHandler>?>()
                ?.let { getIntentHandlerOf(it) }
                ?: run { return unknownIntentException(intentName) }
        }
        val alexaResponse = intentHandler.onBuiltInIntent(builtInIntent, alexaRequest)
        return generateResponse(intentHandler, alexaRequest, alexaResponse)
    }

    override fun handleElementSelectedRequest(alexaRequest: AlexaRequest<ElementSelectedRequest>): AlexaResponse {
        logger.info("=========================== ElementSelectedRequest =========================")
        val intentName = alexaRequest.session?.attribute<String>(INTENT_CONTEXT)
            ?: alexaRequest.request.token.split("\\|".toRegex()).first()
        val intentHandler = getIntentHandlerOf(intentName)
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

    override fun handleConnectionsResponseRequest(
        alexaRequest: AlexaRequest<ConnectionsResponseRequest>
    ): AlexaResponse {
        logger.info("=========================== Connections.Response =========================")
        return intentHandlerInstances[Requester::class]?.onConnectionsResponse(alexaRequest) ?: run {
            lookupIntentHandlerFromAnnotation<Requester> { result ->
                when (result) {
                    is Result.Content -> result.intentHandler.onConnectionsResponse(alexaRequest)
                    is Result.None -> AlexaResponse.emptyResponse()
                    is Result.Error -> throw result.exception
                }
            }
        }
    }

    override fun handleCanFulfillIntentRequest(alexaRequest: AlexaRequest<CanFulfillIntentRequest>): AlexaResponse {
        logger.info("=========================== CanFulfillIntentRequest =========================")
        return intentHandlerInstances[CanFulfillIntent::class]?.onCanFulfillIntent(alexaRequest) ?: run {
            return lookupIntentHandlerFromAnnotation<CanFulfillIntent> { result ->
                when (result) {
                    is Result.Content -> result.intentHandler.onCanFulfillIntent(alexaRequest)
                    is Result.None -> AlexaResponse.emptyResponse()
                    is Result.Error -> throw result.exception
                }
            }
        }
    }

    override fun handleConnectionsRequest(alexaRequest: AlexaRequest<ConnectionsRequest>): AlexaResponse {
        logger.info("=========================== ConnectionsRequest =========================")
        return intentHandlerInstances[Provider::class]?.onConnectionsRequest(alexaRequest) ?: run {
            return lookupIntentHandlerFromAnnotation<Provider> { result ->
                when (result) {
                    is Result.Content -> result.intentHandler.onConnectionsRequest(alexaRequest)
                    is Result.None -> unsupportedIntent()
                    is Result.Error -> throw result.exception
                }
            }
        }
    }

    override fun handleListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListCreatedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListCreatedEventRequest(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<ListEvents> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onListCreatedEventRequest(alexaRequest)
                        is Result.None -> unsupportedIntent()
                        is Result.Error -> throw result.exception
                    }
                }
            }
    }

    override fun handleListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListUpdatedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListUpdatedEventRequest(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<ListEvents> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onListUpdatedEventRequest(alexaRequest)
                        is Result.None -> unsupportedIntent()
                        is Result.Error -> throw result.exception
                    }
                }
            }
    }

    override fun handleListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse {
        logger.info("=========================== ListDeletedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListDeletedEventRequest(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<ListEvents> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onListDeletedEventRequest(alexaRequest)
                        is Result.None -> unsupportedIntent()
                        is Result.Error -> throw result.exception
                    }
                }
            }
    }

    override fun handleListItemsCreatedEventRequest(
        alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsCreatedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListItemsCreatedEventRequest(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<ListEvents> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onListItemsCreatedEventRequest(alexaRequest)
                        is Result.None -> unsupportedIntent()
                        is Result.Error -> throw result.exception
                    }
                }
            }
    }

    override fun handleListItemsUpdatedEventRequest(
        alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsUpdatedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListItemsUpdatedEventRequest(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<ListEvents> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onListItemsUpdatedEventRequest(alexaRequest)
                        is Result.None -> unsupportedIntent()
                        is Result.Error -> throw result.exception
                    }
                }
            }
    }

    override fun handleListItemsDeletedEventRequest(
        alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsDeletedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListItemsDeletedEventRequest(alexaRequest)
            ?: run {
                return lookupIntentHandlerFromAnnotation<ListEvents> { result ->
                    when (result) {
                        is Result.Content -> result.intentHandler.onListItemsDeletedEventRequest(alexaRequest)
                        is Result.None -> unsupportedIntent()
                        is Result.Error -> throw result.exception
                    }
                }
            }
    }

    private inline fun <reified T : Annotation> lookupIntentHandlerFromAnnotation(
        callback: (Result) -> AlexaResponse
    ): AlexaResponse {
        val annotationName = T::class.simpleName!!
        val classes = findAnnotatedClasses(intentHandlerClasses, T::class)
        logger.debug("Detected ${classes.size} intent classes with $annotationName annotation.")
        return when {
            classes.isEmpty() -> callback(Result.None)
            classes.size > 1 -> callback(Result.Error(illegalAnnotationArgument(annotationName)))
            else -> {
                val kclazz = classes.first()
                logger.debug("Class with $annotationName annotation: ${kclazz.simpleName}")
                val intentHandler = getIntentHandlerOf(kclazz)
                callback(Result.Content(intentHandler))
            }
        }
    }

    private fun generateResponse(
        intentHandler: IntentHandler,
        request: AlexaRequest<*>,
        alexaResponse: AlexaResponse
    ): AlexaResponse {
        return if (intentHandler.isIntentContextLocked(request) &&
            alexaResponse.sessionAttributes[INTENT_CONTEXT] == null
        ) {
            alexaResponse.copy(
                sessionAttributes = alexaResponse.sessionAttributes + Pair(
                    INTENT_CONTEXT,
                    intentHandler::class.java.simpleName
                )
            )
        } else {
            alexaResponse
        }
    }

    private fun unknownIntentException(intentName: String): AlexaResponse {
        throw IllegalArgumentException(
            "It was not possible to map intent $intentName to a Class. " +
                "Please make sure that the Intent class is annotated with @Intent or check intent package location"
        )
    }

    private fun illegalAnnotationArgument(annotation: String): IllegalAnnotationException {
        return IllegalAnnotationException("The skill can only have one @$annotation method.")
    }

    @Suppress("unchecked_cast")
    private fun loadIntentHandlerClasses(): List<KClass<out IntentHandler>> {
        return loadIntentClassesFromPackage()
            .filter { clazz ->
                clazz.superclasses.find { superclazz ->
                    superclazz.simpleName == IntentHandler::class.java.simpleName
                } != null
            }
            .cast()
    }

    /**
     * Look @Intent annotation up
     * @return Map of Kclasses. The Array of mapsTo corresponds to the key and the value is the kClass
     * that has the annotation.
     */
    private fun mapClassesWithIntentAnnotation(): Map<Set<String>, KClass<out IntentHandler>> {
        return findAnnotatedClasses(intentHandlerClasses, Intent::class)
            .map { annotatedClass ->
                val intent = annotatedClass.findAnnotation<Intent>()!!
                val mapsTo = intent.mapsTo.toSet() + annotatedClass.simpleName!!
                mapsTo to annotatedClass
            }.toMap()
    }

    /**
     * Retrieves an instance of a given intentName, if no such instance exists, it will be created, put into the hash
     * and return it
     * @param intentName
     * @return an instance of the intentName
     */
    private fun getIntentHandlerOf(intentName: String): IntentHandler? {
        return intentClasses.entries.find {
            it.key.contains(intentName)
        }?.let {
            getIntentHandlerOf(it.value)
        }
    }

    private fun getIntentHandlerOf(kclazz: KClass<out IntentHandler>) =
        intentHandlerInstances.getOrPut(kclazz) { kclazz.createInstance() }

    sealed class Result {
        object None : Result()
        data class Error(val exception: Exception) : Result()
        data class Content(val intentHandler: IntentHandler) : Result()
    }
}
