/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.MessageReceivedRequest
import com.hp.kalexa.model.response.AlexaResponse

interface MessageReceivedRequestHandler : BaseHandler {
    /**
     * Handles Message Received Request coming from Alexa
     */
    fun onMessageReceivedRequest(alexaRequest: AlexaRequest<MessageReceivedRequest>): AlexaResponse
}
