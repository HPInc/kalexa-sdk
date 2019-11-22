/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.response.AlexaResponse

interface StopIntentHandler : BaseHandler {
    /**
     * Handles Stop Built In Intent coming from Alexa.
     */
    override fun onStopIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse
}
