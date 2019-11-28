/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.handler.BaseHandlerRepository
import com.hp.kalexa.core.intent.MessageReceivedRequestHandler
import com.hp.kalexa.core.intent.ReminderEventsHandler
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.MessageReceivedRequest
import com.hp.kalexa.model.response.AlexaResponse
import org.apache.logging.log4j.LogManager

class DefaultMessagingRequestHandler(repository: BaseHandlerRepository) : MessagingRequestHandler,
    AbstractRequestHandler(repository) {

    private val logger = LogManager.getLogger(DefaultMessagingRequestHandler::class.java)

    override fun handleMessageReceivedRequest(alexaRequest: AlexaRequest<MessageReceivedRequest>): AlexaResponse {
        logger.info("=========================== MessageReceivedRequest =========================")
        val messageHandler: MessageReceivedRequestHandler? = getHandler(ReminderEventsHandler::class)?.cast()
        return messageHandler?.let {
            val alexaResponse = messageHandler.onMessageReceivedRequest(alexaRequest)
            return generateResponse(messageHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }
}
