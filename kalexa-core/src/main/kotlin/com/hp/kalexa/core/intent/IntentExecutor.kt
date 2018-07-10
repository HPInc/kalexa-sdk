package com.hp.kalexa.core.intent

import com.hp.kalexa.core.handler.SpeechHandler.Companion.INTENT_CONTEXT
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.core.util.IntentUtil.defaultGreetings
import com.hp.kalexa.core.util.IntentUtil.finish
import com.hp.kalexa.core.util.IntentUtil.goodbye
import com.hp.kalexa.core.util.IntentUtil.helpIntent
import com.hp.kalexa.core.util.IntentUtil.retryIntent
import com.hp.kalexa.core.util.IntentUtil.unsupportedIntent
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Session
import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.request.event.*
import com.hp.kalexa.model.response.AlexaResponse

abstract class IntentExecutor {

    var sessionAttributes: MutableMap<String, Any?> = mutableMapOf()
    var session: Session? = null
    lateinit var context: Context
    var version: String = "1.0"

    /**
     * @return Skill name defined in the environment variable SKILL_NAME
     */
    fun getSkillName() = Util.getSkillName()

    /**
     * Handles Launch Intent Request coming from Alexa
     */
    open fun onLaunchIntent(request: LaunchRequest) = defaultGreetings()

    /**
     * Handles Intent Request coming from Alexa
     */
    open fun onIntentRequest(request: IntentRequest) = AlexaResponse.emptyResponse()

    /**
     * Handles Connections Response Request coming from Alexa. This is the result from a fulfiller skill when using skill connections
     */
    open fun onConnectionsResponse(request: ConnectionsResponseRequest): AlexaResponse = AlexaResponse.emptyResponse()

    /**
     * Handles Connections Request coming from Alexa. This is the request from a requester skill when using skill connections
     * Should be used combined with @Fulfiller annotation
     */
    open fun onConnectionsRequest(request: ConnectionsRequest): AlexaResponse = AlexaResponse.emptyResponse()

    /**
     * Handles The Built In Intents coming from Alexa.
     */
    open fun onBuiltInIntent(builtInIntent: BuiltInIntent, request: IntentRequest): AlexaResponse {
        return when (builtInIntent) {
            BuiltInIntent.YES_INTENT -> onYesIntent(request)
            BuiltInIntent.CANCEL_INTENT -> onCancelIntent(request)
            BuiltInIntent.NO_INTENT -> onNoIntent(request)
            BuiltInIntent.STOP_INTENT -> onStopIntent(request)
            BuiltInIntent.HELP_INTENT -> onHelpIntent(request)
            else -> onDefaultBuiltInIntent(request)
        }
    }

    /**
     * Handles element selected coming from Alexa. Basically when the user touches the screen
     */
    open fun onElementSelected(request: ElementSelectedRequest) = AlexaResponse.emptyResponse()

    /**
     * Handles Built in Intent that has no INTENT_CONTEXT or Intent name attached to the context
     */
    open fun onUnknownIntentContext(builtInIntent: BuiltInIntent): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    /**
     * Handles Yes Built In Intent coming from Alexa.
     */
    open fun onYesIntent(request: IntentRequest): AlexaResponse {
        return onDefaultBuiltInIntent(request)
    }

    /**
     * Handles No Built In Intent coming from Alexa.
     */
    open fun onNoIntent(request: IntentRequest): AlexaResponse {
        return finish()
    }

    /**
     * Handles Stop Built In Intent coming from Alexa.
     */
    open fun onStopIntent(request: IntentRequest): AlexaResponse {
        return goodbye()
    }

    /**
     * Handles Cancel Built In Intent coming from Alexa.
     */
    open fun onCancelIntent(request: IntentRequest): AlexaResponse {
        return finish()
    }

    /**
     * Handles Help Built In Intent coming from Alexa.
     */
    open fun onHelpIntent(request: IntentRequest): AlexaResponse {
        return helpIntent()
    }

    /**
     * Handles FallbackIntent Built In Intent coming from Alexa.
     */
    open fun onFallbackIntent(request: IntentRequest): AlexaResponse {
        return unsupportedIntent()
    }

    /**
     * Handles other Built In Intents coming from Alexa.
     */
    open fun onDefaultBuiltInIntent(request: IntentRequest): AlexaResponse {
        return retryIntent(sessionAttributes)
    }

    /**
     * Locks the context on the intent caller.
     */
    fun lockIntentContext() {
        sessionAttributes[INTENT_CONTEXT] = this::class.java.simpleName
    }

    /**
     * Unlocks the context on the intent caller.
     */
    fun unlockIntentContext() {
        sessionAttributes[INTENT_CONTEXT] = null
    }

    fun isIntentContextLocked() = sessionAttributes[INTENT_CONTEXT] != null


    fun hasDisplay() = IntentUtil.hasDisplay(context)

    open fun onListItemsUpdatedEventRequest(request: ListItemsUpdatedEventRequest): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    open fun onListItemsDeletedEventRequest(request: ListItemsDeletedEventRequest): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    open fun onListItemsCreatedEventRequest(request: ListItemsCreatedEventRequest): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    open fun onListDeletedEventRequest(request: ListDeletedEventRequest): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    open fun onListUpdatedEventRequest(request: ListUpdatedEventRequest): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    open fun onListCreatedEventRequest(request: ListCreatedEventRequest): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

}