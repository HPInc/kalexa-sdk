/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.handler.BaseHandlerRepository
import com.hp.kalexa.core.intent.InputHandlerEventRequestHandler
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.InputHandlerEventRequest
import com.hp.kalexa.model.response.AlexaResponse
import org.apache.logging.log4j.LogManager

class DefaultGameEngineRequestHandler(repository: BaseHandlerRepository) : GameEngineRequestHandler,
    AbstractRequestHandler(repository) {
    private val logger = LogManager.getLogger(DefaultGameEngineRequestHandler::class.java)

    override fun handleInputHandlerEventRequest(alexaRequest: AlexaRequest<InputHandlerEventRequest>): AlexaResponse {
        logger.info("=========================== InputHandlerEventRequest =========================")
        val inputHandler: InputHandlerEventRequestHandler? = getHandler(InputHandlerEventRequestHandler::class)?.cast()
        return inputHandler?.let {
            val alexaResponse = inputHandler.onInputHandlerEventRequest(alexaRequest)
            return generateResponse(inputHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }
}
