/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.core.handler.SpeechHandler.Companion.INTENT_CONTEXT
import com.hp.kalexa.core.util.IntentUtil.finish
import com.hp.kalexa.core.util.IntentUtil.goodbye
import com.hp.kalexa.core.util.IntentUtil.retryIntent
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.response.AlexaResponse

interface BaseHandler {

    /**
     * @return Skill name defined in the environment variable SKILL_NAME
     */
    fun getSkillName() = Util.getSkillName()

    /**
     * Handles The Built In Intents coming from Alexa.
     */
    fun onBuiltInIntent(builtInIntent: BuiltInIntent, alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return when (builtInIntent) {
            BuiltInIntent.YES_INTENT -> onYesIntent(alexaRequest)
            BuiltInIntent.CANCEL_INTENT -> onCancelIntent(alexaRequest)
            BuiltInIntent.NO_INTENT -> onNoIntent(alexaRequest)
            BuiltInIntent.STOP_INTENT -> onStopIntent(alexaRequest)
            else -> onDefaultBuiltInIntent(alexaRequest)
        }
    }

    /**
     * Handles element selected coming from Alexa. Basically when the user touches the screen
     */
    fun onElementSelected(alexaRequest: AlexaRequest<ElementSelectedRequest>) = AlexaResponse.emptyResponse()

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
}
