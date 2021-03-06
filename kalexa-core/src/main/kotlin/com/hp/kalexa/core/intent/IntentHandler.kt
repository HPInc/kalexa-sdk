/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.response.AlexaResponse

interface IntentHandler : BaseHandler {

    /**
     * Handles Intent Request coming from Alexa
     */
    fun onIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse
}
