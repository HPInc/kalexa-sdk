package com.hp.kalexa.core.handler

import com.hp.kalexa.core.annotation.*
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
import com.hp.kalexa.model.extension.attribute
import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.request.event.*
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.alexaResponse
import com.sun.xml.internal.txw2.IllegalAnnotationException
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses
import kotlin.system.measureTimeMillis


open class DefaultSpeechHandler : SpeechHandler {
    private val logger = LogManager.getLogger(DefaultSpeechHandler::class.java)

    private val intentHandlerClasses: List<KClass<out IntentHandler>> = loadIntentHandlerClasses()
    private val intentClasses: Map<Set<String>, KClass<out IntentHandler>> = mapClassesWithIntentAnnotation()
    private val intentHandlerInstances = mutableMapOf<KClass<out Any>, IntentHandler>()

    override fun handleSessionStartedRequest(envelope: AlexaRequestEnvelope<SessionStartedRequest>) = AlexaResponse.emptyResponse()

    override fun handleLaunchRequest(envelope: AlexaRequestEnvelope<LaunchRequest>): AlexaResponse {
        logger.info("=========================== LaunchRequest =========================")
        logger.debug("Looking for LaunchIntent intents in ${getIntentPackage()}")
        return intentHandlerInstances[LaunchIntent::class]?.onLaunchIntent(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<LaunchIntent>(envelope) { result ->
                        when (result) {
                            is Result.Content -> {
                                val alexaResponse = result.intentHandler.onLaunchIntent(envelope.request)
                                generateResponse(result.intentHandler, alexaResponse)
                            }
                            is Result.None -> defaultGreetings()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    override fun handleIntentRequest(envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        logger.info("=========================== IntentRequest =========================")
        val intentName = envelope.session?.attribute<String>(INTENT_CONTEXT) ?: envelope.request.intent.name
        val builtInIntent = BuiltInIntent.getBuiltInIntent(envelope.request.intent.name)
        logger.debug("Intent name: $intentName - Built in Intent: $builtInIntent")
        return when {
            builtInIntent == null -> customIntent(intentName, envelope)
            intentName == BuiltInIntent.FALLBACK_INTENT.rawValue -> fallbackIntent(envelope)
            intentName == BuiltInIntent.HELP_INTENT.rawValue -> helpIntent(envelope)
            intentName == builtInIntent.rawValue -> unknownIntentContext(builtInIntent, envelope)
            else -> builtInIntent(intentName, builtInIntent, envelope)
        }
    }

    private fun customIntent(intentName: String, envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        val intentHandler = getIntentHandlerOf(intentName, envelope)
        return intentHandler?.let { handler ->
            val alexaResponse = handler.onIntentRequest(envelope.request)
            generateResponse(handler, alexaResponse)
        } ?: unknownIntentException(intentName)
    }

    private fun fallbackIntent(envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        logger.info("=========================== Fallback Intent =========================")
        return intentHandlerInstances[FallbackIntent::class]?.onFallbackIntent(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<FallbackIntent>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onFallbackIntent(envelope.request)
                            is Result.None -> unsupportedIntent()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    private fun helpIntent(envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        logger.info("=========================== Help Intent =========================")
        return intentHandlerInstances[HelpIntent::class]?.onHelpIntent(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<HelpIntent>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onHelpIntent(envelope.request)
                            is Result.None -> helpIntent()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    private fun unknownIntentContext(builtInIntent: BuiltInIntent, envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        return intentHandlerInstances[RecoverIntentContext::class]?.onUnknownIntentContext(builtInIntent)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<RecoverIntentContext>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onUnknownIntentContext(builtInIntent)
                            is Result.None -> defaultBuiltInResponse(builtInIntent, envelope.session?.attributes)
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    private fun builtInIntent(intentName: String, builtInIntent: BuiltInIntent, envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse {
        val intentHandler = getIntentHandlerOf(intentName, envelope)
        return intentHandler?.let { handler ->
            val alexaResponse = handler.onBuiltInIntent(builtInIntent, envelope.request)
            generateResponse(handler, alexaResponse)
        } ?: unknownIntentException(intentName)
    }

    override fun handleElementSelectedRequest(envelope: AlexaRequestEnvelope<ElementSelectedRequest>): AlexaResponse {
        logger.info("=========================== ElementSelectedRequest =========================")
        val intentName = envelope.session?.attribute<String>(INTENT_CONTEXT)
                ?: envelope.request.token.split("\\|".toRegex()).first()
        val intentHandler = getIntentHandlerOf(intentName, envelope)
        return intentHandler?.let {
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
        logger.info("=========================== Connections.Response =========================")
        val intent = envelope.request.token.split("\\|").first()
        val intentHandler = getIntentHandlerOf(intent, envelope)
        return intentHandler?.let {
            val alexaResponse = it.onConnectionsResponse(envelope.request)
            generateResponse(it, alexaResponse)
        } ?: unknownIntentException(intent)
    }

    override fun handleConnectionsRequest(envelope: AlexaRequestEnvelope<ConnectionsRequest>): AlexaResponse {
        logger.info("=========================== ConnectionsRequest =========================")
        return intentHandlerInstances[FulfillerIntent::class]?.onConnectionsRequest(envelope.request) ?: run {
            return lookupIntentHandlerFromAnnotation<FulfillerIntent>(envelope) { result ->
                when (result) {
                    is Result.Content -> result.intentHandler.onConnectionsRequest(envelope.request)
                    is Result.None -> unsupportedIntent()
                    is Result.Error -> throw result.exception
                }
            }
        }
    }

    override fun handleListCreatedEventRequest(envelope: AlexaRequestEnvelope<ListCreatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListCreatedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListCreatedEventRequest(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<ListEvents>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onListCreatedEventRequest(envelope.request)
                            is Result.None -> unsupportedIntent()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    override fun handleListUpdatedEventRequest(envelope: AlexaRequestEnvelope<ListUpdatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListUpdatedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListUpdatedEventRequest(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<ListEvents>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onListUpdatedEventRequest(envelope.request)
                            is Result.None -> unsupportedIntent()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    override fun handleListDeletedEventRequest(envelope: AlexaRequestEnvelope<ListDeletedEventRequest>): AlexaResponse {
        logger.info("=========================== ListDeletedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListDeletedEventRequest(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<ListEvents>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onListDeletedEventRequest(envelope.request)
                            is Result.None -> unsupportedIntent()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    override fun handleListItemsCreatedEventRequest(envelope: AlexaRequestEnvelope<ListItemsCreatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListItemsCreatedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListItemsCreatedEventRequest(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<ListEvents>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onListItemsCreatedEventRequest(envelope.request)
                            is Result.None -> unsupportedIntent()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    override fun handleListItemsUpdatedEventRequest(envelope: AlexaRequestEnvelope<ListItemsUpdatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListItemsUpdatedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListItemsUpdatedEventRequest(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<ListEvents>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onListItemsUpdatedEventRequest(envelope.request)
                            is Result.None -> unsupportedIntent()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    override fun handleListItemsDeletedEventRequest(envelope: AlexaRequestEnvelope<ListItemsDeletedEventRequest>): AlexaResponse {
        logger.info("=========================== ListItemsDeletedEventRequest =========================")
        return intentHandlerInstances[ListEvents::class]?.onListItemsDeletedEventRequest(envelope.request)
                ?: run {
                    return lookupIntentHandlerFromAnnotation<ListEvents>(envelope) { result ->
                        when (result) {
                            is Result.Content -> result.intentHandler.onListItemsDeletedEventRequest(envelope.request)
                            is Result.None -> unsupportedIntent()
                            is Result.Error -> throw result.exception
                        }
                    }
                }
    }

    private inline fun <reified T : Annotation> lookupIntentHandlerFromAnnotation(envelope: AlexaRequestEnvelope<*>,
                                                                                  callback: (Result) -> AlexaResponse): AlexaResponse {
        val annotationName = T::class.simpleName!!
        val classes = findAnnotatedClasses(intentHandlerClasses, T::class)
        logger.debug("Detected ${classes.size} intent classes with $annotationName annotation.")
        return when {
            classes.isEmpty() -> callback(Result.None)
            classes.size > 1 -> callback(Result.Error(illegalAnnotationArgument(annotationName)))
            else -> {
                val kclazz = classes.first()
                logger.debug("Class with $annotationName annotation: ${kclazz.simpleName}")
                val intentHandler = getIntentHandlerOf(kclazz, envelope) as IntentHandler
                intentHandlerInstances[T::class] = intentHandler
                callback(Result.Content(intentHandler))
            }
        }
    }

    private fun generateResponse(intentHandler: IntentHandler, alexaResponse: AlexaResponse): AlexaResponse {
        return if (intentHandler.isIntentContextLocked() && alexaResponse.sessionAttributes[INTENT_CONTEXT] == null) {
            alexaResponse.copy(sessionAttributes = alexaResponse.sessionAttributes + Pair(INTENT_CONTEXT, intentHandler::class.java.simpleName))
        } else {
            alexaResponse
        }
    }

    private fun unknownIntentException(intentName: String): AlexaResponse {
        throw IllegalArgumentException("It was not possible to map intent $intentName to a Class. " +
                "Please make sure that the Intent class is annotated with @Intent or check intent package location")
    }

    private fun illegalAnnotationArgument(annotation: String): IllegalAnnotationException {
        return IllegalAnnotationException("The skill can only have one @$annotation method.")
    }

    @Suppress("unchecked_cast")
    private fun loadIntentHandlerClasses(): List<KClass<out IntentHandler>> {
        return loadIntentClassesFromPackage()
                .filter { clazz -> clazz.superclasses.find { superclazz -> superclazz.simpleName == IntentHandler::class.java.simpleName } != null }
                .cast()
    }

    /**
     * Look @Intent annotation up
     * @return Map of Kclasses. The Array of mapsTo corresponds to the key and the value is the kClass that has the annotation.
     */
    private fun mapClassesWithIntentAnnotation(): Map<Set<String>, KClass<out IntentHandler>> {
        return findAnnotatedClasses(intentHandlerClasses, Intent::class)
                .map { annotatedClass ->
                    val intent = annotatedClass.findAnnotation<Intent>()!!
                    val mapsTo = setOf(*intent.mapsTo, annotatedClass.simpleName!!)
                    mapsTo to annotatedClass
                }.toMap()
    }


    /**
     * Retrieves an instance of a given intentName, if no such instance exists, it will be created, put into the hash
     * and return it
     * @param intentName
     * @return an instance of the intentName
     */
    private fun getIntentHandlerOf(intentName: String, envelope: AlexaRequestEnvelope<*>): IntentHandler? {
        return intentClasses.entries.find {
            it.key.contains(intentName)
        }?.let {
            getIntentHandlerOf(it.value, envelope)
        }
    }


    private fun getIntentHandlerOf(kclazz: KClass<out IntentHandler>, envelope: AlexaRequestEnvelope<*>): IntentHandler? {
        val intentHandler: IntentHandler = intentHandlerInstances.getOrPut(kclazz) { kclazz.createInstance() }
        intentHandler.sessionAttributes = envelope.session?.attributes ?: mutableMapOf()
        intentHandler.session = envelope.session
        intentHandler.context = envelope.context
        intentHandler.version = envelope.version
        return intentHandler
    }

    // Sealed
    sealed class Result {
        object None : Result()
        data class Error(val exception: Exception) : Result()
        data class Content(val intentHandler: IntentHandler) : Result()
    }
}