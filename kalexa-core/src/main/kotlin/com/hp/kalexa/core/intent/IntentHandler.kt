/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.core.handler.SpeechHandler.Companion.INTENT_CONTEXT
import com.hp.kalexa.core.util.IntentUtil.defaultGreetings
import com.hp.kalexa.core.util.IntentUtil.finish
import com.hp.kalexa.core.util.IntentUtil.goodbye
import com.hp.kalexa.core.util.IntentUtil.helpIntent
import com.hp.kalexa.core.util.IntentUtil.retryIntent
import com.hp.kalexa.core.util.IntentUtil.unsupportedIntent
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.request.ConnectionsRequest
import com.hp.kalexa.model.request.ConnectionsResponseRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse

interface IntentHandler {

    /**
     * @return Skill name defined in the environment variable SKILL_NAME
     */
    fun getSkillName() = Util.getSkillName()

    /**
     * Handles Launch Intent Request coming from Alexa
     */
    fun onLaunchIntent(alexaRequest: AlexaRequest<LaunchRequest>) = defaultGreetings()

    /**
     * Handles Intent Request coming from Alexa
     */
    fun onIntentRequest(alexaRequest: AlexaRequest<IntentRequest>) = AlexaResponse.emptyResponse()

    /**
     * Handles Connections Response Request coming from Alexa. This is the result from a fulfiller skill
     * when using skill connections
     */
    fun onConnectionsResponse(alexaRequest: AlexaRequest<ConnectionsResponseRequest>): AlexaResponse =
        AlexaResponse.emptyResponse()

    /**
     * Handles Connections Request coming from Alexa. This is the request from a requester skill when using
     * skill connections.
     * Should be used combined with @Fulfiller annotation
     */
    fun onConnectionsRequest(alexaRequest: AlexaRequest<ConnectionsRequest>): AlexaResponse =
        AlexaResponse.emptyResponse()

    /**
     * Handles The Built In Intents coming from Alexa.
     */
    fun onBuiltInIntent(builtInIntent: BuiltInIntent, alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return when (builtInIntent) {
            BuiltInIntent.YES_INTENT -> onYesIntent(alexaRequest)
            BuiltInIntent.CANCEL_INTENT -> onCancelIntent(alexaRequest)
            BuiltInIntent.NO_INTENT -> onNoIntent(alexaRequest)
            BuiltInIntent.STOP_INTENT -> onStopIntent(alexaRequest)
            BuiltInIntent.HELP_INTENT -> onHelpIntent(alexaRequest)
            else -> onDefaultBuiltInIntent(alexaRequest)
        }
    }

    fun onCanFulfillIntent(alexaRequest: AlexaRequest<CanFulfillIntentRequest>): AlexaResponse =
        AlexaResponse.emptyResponse()

    /**
     * Handles element selected coming from Alexa. Basically when the user touches the screen
     */
    fun onElementSelected(alexaRequest: AlexaRequest<ElementSelectedRequest>) = AlexaResponse.emptyResponse()

    /**
     * Handles Built in Intent that has no INTENT_CONTEXT or Intent name attached to the context
     */
    fun onUnknownIntentContext(builtInIntent: BuiltInIntent): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    /**
     * Handles Yes Built In Intent coming from Alexa.
     */
    fun onYesIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return onDefaultBuiltInIntent(alexaRequest)
    }

    /**
     * Handles No Built In Intent coming from Alexa.
     */
    fun onNoIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return finish()
    }

    /**
     * Handles Stop Built In Intent coming from Alexa.
     */
    fun onStopIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return goodbye()
    }

    /**
     * Handles Cancel Built In Intent coming from Alexa.
     */
    fun onCancelIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return finish()
    }

    /**
     * Handles Help Built In Intent coming from Alexa.
     */
    fun onHelpIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return helpIntent()
    }

    /**
     * Handles FallbackIntent Built In Intent coming from Alexa.
     */
    fun onFallbackIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return unsupportedIntent()
    }

    /**
     * Handles other Built In Intents coming from Alexa.
     */
    fun onDefaultBuiltInIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return retryIntent(alexaRequest.session?.attributes ?: mutableMapOf())
    }

    /**
     * Locks the context on the intent caller.
     */
    fun lockIntentContext(alexaRequest: AlexaRequest<*>) {
        alexaRequest.session?.attributes?.set(INTENT_CONTEXT, this::class.java.simpleName)
    }

    /**
     * Unlocks the context on the intent caller.
     */
    fun unlockIntentContext(alexaRequest: AlexaRequest<*>) {
        alexaRequest.session?.attributes?.remove(INTENT_CONTEXT)
    }

    fun isIntentContextLocked(alexaRequest: AlexaRequest<*>) =
        alexaRequest.session?.attributes?.get(INTENT_CONTEXT) != null

    fun hasDisplay(alexaRequest: AlexaRequest<*>) = alexaRequest.context.hasDisplay()

    fun onListItemsUpdatedEventRequest(alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListItemsDeletedEventRequest(alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListItemsCreatedEventRequest(alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }
}
