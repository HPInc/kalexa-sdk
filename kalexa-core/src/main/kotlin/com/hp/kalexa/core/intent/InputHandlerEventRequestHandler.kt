/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.InputHandlerEventRequest
import com.hp.kalexa.model.response.AlexaResponse

interface InputHandlerEventRequestHandler : BaseHandler {
    /**
     * Handles Input Handler Event Request coming from Alexa
     */
    fun onInputHandlerEventRequest(alexaRequest: AlexaRequest<InputHandlerEventRequest>): AlexaResponse
}
