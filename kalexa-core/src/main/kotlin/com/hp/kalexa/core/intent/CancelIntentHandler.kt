/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.response.AlexaResponse

interface CancelIntentHandler : BaseHandler {

    /**
     * Handles Cancel Built In Intent coming from Alexa.
     */
    override fun onCancelIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse
}
