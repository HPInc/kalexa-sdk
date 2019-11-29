/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.InputHandlerEventRequest
import com.hp.kalexa.model.response.AlexaResponse

interface GameEngineRequestHandler {
    fun handleInputHandlerEventRequest(alexaRequest: AlexaRequest<InputHandlerEventRequest>): AlexaResponse
}
