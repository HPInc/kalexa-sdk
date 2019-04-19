/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.annotation.CanFulfillIntent
import com.hp.kalexa.core.annotation.Intent
import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.extension.hasSuperClassOf
import com.hp.kalexa.core.handler.SpeechHandler.Companion.INTENT_CONTEXT
import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.CanFulfillIntentHandler
import com.hp.kalexa.core.intent.FallbackIntentHandler
import com.hp.kalexa.core.intent.HelpIntentHandler
import com.hp.kalexa.core.intent.IntentHandler
import com.hp.kalexa.core.intent.LaunchRequestHandler
import com.hp.kalexa.core.intent.ListEventsHandler
import com.hp.kalexa.core.intent.ProviderHandler
import com.hp.kalexa.core.intent.RecoverIntentContextHandler
import com.hp.kalexa.core.intent.RequesterHandler
import com.hp.kalexa.core.util.IntentUtil.defaultBuiltInResponse
import com.hp.kalexa.core.util.IntentUtil.defaultGreetings
import com.hp.kalexa.core.util.IntentUtil.helpIntent
import com.hp.kalexa.core.util.IntentUtil.unsupportedIntent
import com.hp.kalexa.core.util.Util.getScanPackage
import com.hp.kalexa.core.util.Util.loadClassesFromPackage
import com.hp.kalexa.model.extension.attribute
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.BaseIntentRequest
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
import com.hp.kalexa.model.response.dsl.alexaResponse
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses

open class ConcreteSpeechHandler(instances: List<BaseHandler> = emptyList()) : SpeechHandler {

    private val logger = LogManager.getLogger(ConcreteSpeechHandler::class.java)

    private val intentHandlerClasses: Set<KClass<out BaseHandler>> =
        getClassesFrom(instances) + loadIntentHandlerClasses()
    private val intentMap: Map<Set<String>, KClass<out BaseHandler>> = mapIntentHandlersOf<Intent>(IntentHandler::class)
    private val canFulfillMap: Map<Set<String>, KClass<out BaseHandler>> = mapIntentHandlersOf<CanFulfillIntent>(
        CanFulfillIntentHandler::class
    )
    private val handlerInstances: MutableMap<String, BaseHandler> = toMap(instances)

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

    override fun handleConnectionsResponseRequest(
        alexaRequest: AlexaRequest<ConnectionsResponseRequest>
    ): AlexaResponse {
        logger.info("=========================== Connections.Response =========================")
        val requesterHandler: RequesterHandler? = getHandler(RequesterHandler::class)?.cast()

        return requesterHandler?.let {
            val alexaResponse = requesterHandler.onConnectionsResponse(alexaRequest)
            return generateResponse(requesterHandler, alexaRequest, alexaResponse)
        } ?: AlexaResponse.emptyResponse()
    }

    override fun handleConnectionsRequest(alexaRequest: AlexaRequest<ConnectionsRequest>): AlexaResponse {
        logger.info("=========================== ConnectionsRequest =========================")
        val providerHandler: ProviderHandler? = getHandler(ProviderHandler::class)?.cast()

        return providerHandler?.let {
            val alexaResponse = providerHandler.onConnectionsRequest(alexaRequest)
            return generateResponse(providerHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    override fun handleListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListCreatedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()

        return listHandler?.let {
            val alexaResponse = listHandler.onListCreatedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    override fun handleListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListUpdatedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListUpdatedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    override fun handleListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse {
        logger.info("=========================== ListDeletedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListDeletedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    override fun handleListItemsCreatedEventRequest(
        alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsCreatedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListItemsCreatedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    override fun handleListItemsUpdatedEventRequest(
        alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsUpdatedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListItemsUpdatedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
    }

    override fun handleListItemsDeletedEventRequest(
        alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsDeletedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListItemsDeletedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: unsupportedIntent()
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
            handlerInstances.keys
                .find { it == intentName }
                ?.let { handlerInstances[intentName] }
                ?.let { getIntentHandlerOf(it::class) }
                ?: run { return unknownIntentException(intentName) }
        }
        val alexaResponse = intentHandler.onBuiltInIntent(builtInIntent, alexaRequest)
        return generateResponse(intentHandler, alexaRequest, alexaResponse)
    }

    /**
     * Generates the Alexa response based on the INTENT_CONTEXT. If INTENT_CONTEXT is enabled,
     * then it will add the intent context value (which is the class name of the current intent handler)
     * to the session attributes.
     */
    private fun generateResponse(
        intentHandler: BaseHandler,
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
     * Loads all BaseHandler classes from the SCAN_PACKAGE environment variable.
     */
    @Suppress("unchecked_cast")
    private fun loadIntentHandlerClasses(): Set<KClass<out BaseHandler>> {
        return loadClassesFromPackage()
            .filter { clazz ->
                clazz.superclasses.find { superclazz ->
                    superclazz.simpleName == BaseHandler::class.java.simpleName || superclazz.superclasses.find {
                        it.simpleName == BaseHandler::class.java.simpleName
                    } != null
                } != null
            }
            .toSet()
            .cast()
    }

    /**
     * Look a given annotation up
     * @return Map of Kclasses. The Array of mapsTo corresponds to the key and kClass is the value
     */
    private inline fun <reified T : Annotation> mapIntentHandlersOf(kClass: KClass<out BaseHandler>):
        Map<Set<String>, KClass<out BaseHandler>> {
        return intentHandlerClasses.filter {
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

    /**
     * Looks for the Handler instance in handler instances map using the given class name as key,
     * if no such instance exists with the given key,
     * then it looks for the instance again in the handler instances map by iterating over the values
     * and checking if each value has a super class of the given class.
     * And still if no such element is found and the given class implements some Handler or has a super class of
     * the given class, then a new instance is created and added to the Handler Instances map and the instance is returned.
     * @param clazz of the wanted handler instance.
     * @return handler instance.
     */
    private fun getHandler(clazz: KClass<out BaseHandler>): BaseHandler? {
        return handlerInstances[clazz.simpleName!!]
            ?: run {
                handlerInstances.values.find { it::class.hasSuperClassOf(clazz) }
                    ?.let { handler ->
                        handlerInstances[handler::class.simpleName!!] = handler
                        handler
                    }
            } ?: run {
                intentHandlerClasses.find { it == clazz || it.hasSuperClassOf(clazz) }
                    ?.createInstance()
                    ?.let { instance ->
                        handlerInstances[instance::class.simpleName!!] = instance
                        instance
                    }
            }
    }

    /**
     * Checks if any of the entry keys from the classes map is equal to the given intent name, if so, it will return
     * a instance of the corresponding intent handler.
     * @param intentName to be retrieved
     * @param classes map containing all the annotated classes
     */
    private fun getIntentHandlerOf(
        intentName: String,
        classes: Map<Set<String>, KClass<out BaseHandler>>
    ): BaseHandler? {
        return classes.entries.find {
            it.key.contains(intentName)
        }?.let {
            getIntentHandlerOf(it.value)
        }
    }

    /**
     * Retrieves an instance of a given intentName, if no such instance exists, it will be created, put it into the hash
     * and return it
     * @return an instance of the intentName
     */
    private fun getIntentHandlerOf(kclazz: KClass<out BaseHandler>) =
        handlerInstances.getOrPut(kclazz.simpleName!!) { kclazz.createInstance() }

    private fun toMap(intentHandlers: List<BaseHandler>) =
        intentHandlers.map { it::class.simpleName!! to it }.toMap().toMutableMap()

    private fun getClassesFrom(intentHandlers: List<BaseHandler>) =
        intentHandlers.map { it::class }.toSet()
}
