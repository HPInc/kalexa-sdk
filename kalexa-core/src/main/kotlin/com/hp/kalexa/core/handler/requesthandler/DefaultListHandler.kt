/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.handler.BaseHandlerRepository
import com.hp.kalexa.core.intent.ListEventsHandler
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse
import org.apache.logging.log4j.LogManager

class DefaultListHandler(repository: BaseHandlerRepository) : ListHandler, AbstractRequestHandler(repository) {
    private val logger = LogManager.getLogger(DefaultListHandler::class.java)

    override fun handleListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListCreatedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()

        return listHandler?.let {
            val alexaResponse = listHandler.onListCreatedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse {
        logger.info("=========================== ListUpdatedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListUpdatedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse {
        logger.info("=========================== ListDeletedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListDeletedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleListItemsCreatedEventRequest(
        alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsCreatedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListItemsCreatedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleListItemsUpdatedEventRequest(
        alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsUpdatedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListItemsUpdatedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleListItemsDeletedEventRequest(
        alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ListItemsDeletedEventRequest =========================")
        val listHandler: ListEventsHandler? = getHandler(ListEventsHandler::class)?.cast()
        return listHandler?.let {
            val alexaResponse = listHandler.onListItemsDeletedEventRequest(alexaRequest)
            return generateResponse(listHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }
}
