/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.handler.BaseHandlerRepository
import com.hp.kalexa.core.intent.ReminderEventsHandler
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.event.reminder.ReminderCreatedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderDeletedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderStartedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderStatusChangedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse
import org.apache.logging.log4j.LogManager

class DefaultReminderHandler(repository: BaseHandlerRepository) : ReminderHandler, AbstractRequestHandler(repository) {
    private val logger = LogManager.getLogger(DefaultReminderHandler::class.java)

    override fun handleReminderCreatedEventRequest(
        alexaRequest: AlexaRequest<ReminderCreatedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ReminderCreatedEventRequest =========================")
        val reminderHandler: ReminderEventsHandler? = getHandler(ReminderEventsHandler::class)?.cast()
        return reminderHandler?.let {
            val alexaResponse = reminderHandler.onReminderCreatedEventRequest(alexaRequest)
            return generateResponse(reminderHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleReminderDeletedEventRequest(
        alexaRequest: AlexaRequest<ReminderDeletedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ReminderDeletedEventRequest =========================")
        val reminderHandler: ReminderEventsHandler? = getHandler(ReminderEventsHandler::class)?.cast()
        return reminderHandler?.let {
            val alexaResponse = reminderHandler.onReminderDeletedEventRequest(alexaRequest)
            return generateResponse(reminderHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleReminderStartedEventRequest(
        alexaRequest: AlexaRequest<ReminderStartedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ReminderStartedEventRequest =========================")
        val reminderHandler: ReminderEventsHandler? = getHandler(ReminderEventsHandler::class)?.cast()
        return reminderHandler?.let {
            val alexaResponse = reminderHandler.onReminderStartedEventRequest(alexaRequest)
            return generateResponse(reminderHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleReminderStatusChangedEventRequest(
        alexaRequest: AlexaRequest<ReminderStatusChangedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ReminderStatusChangedEventRequest =========================")
        val reminderHandler: ReminderEventsHandler? = getHandler(ReminderEventsHandler::class)?.cast()
        return reminderHandler?.let {
            val alexaResponse = reminderHandler.onReminderStatusChangedEventRequest(alexaRequest)
            return generateResponse(reminderHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleReminderUpdatedEventRequest(
        alexaRequest: AlexaRequest<ReminderUpdatedEventRequest>
    ): AlexaResponse {
        logger.info("=========================== ReminderUpdatedEventRequest =========================")
        val reminderHandler: ReminderEventsHandler? = getHandler(ReminderEventsHandler::class)?.cast()
        return reminderHandler?.let {
            val alexaResponse = reminderHandler.onReminderUpdatedEventRequest(alexaRequest)
            return generateResponse(reminderHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }
}
