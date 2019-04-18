/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.response.AlexaResponse

interface FallbackIntentHandler : BaseHandler {

    /**
     * Handles FallbackIntent Built In Intent coming from Alexa.
     */
    fun onFallbackIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse
}
