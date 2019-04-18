/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.response.AlexaResponse

interface CanFulfillIntentHandler : BaseHandler {

    /**
     * Handles CanFulfill Request coming from Alexa
     * This request verifies if the skill can understand and fulfill the intent request and slots.
     */
    fun onCanFulfillIntent(alexaRequest: AlexaRequest<CanFulfillIntentRequest>): AlexaResponse
}
