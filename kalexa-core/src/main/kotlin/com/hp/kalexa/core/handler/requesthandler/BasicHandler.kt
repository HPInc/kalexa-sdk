/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.SessionEndedRequest
import com.hp.kalexa.model.request.SessionResumedRequest
import com.hp.kalexa.model.request.SessionStartedRequest
import com.hp.kalexa.model.response.AlexaResponse

interface BasicHandler {

    fun handleSessionStartedRequest(alexaRequest: AlexaRequest<SessionStartedRequest>): AlexaResponse

    fun handleLaunchRequest(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse

    fun handleIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse

    fun handleElementSelectedRequest(alexaRequest: AlexaRequest<ElementSelectedRequest>): AlexaResponse

    fun handleSessionEndedRequest(alexaRequest: AlexaRequest<SessionEndedRequest>): AlexaResponse

    fun handleCanFulfillIntentRequest(alexaRequest: AlexaRequest<CanFulfillIntentRequest>): AlexaResponse

    fun handleSessionResumedRequest(alexaRequest: AlexaRequest<SessionResumedRequest>): AlexaResponse

    companion object {
        const val INTENT_CONTEXT = "com.hp.kalexa.intentContext"
    }
}
