/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.event.reminder.ReminderCreatedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderDeletedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderStartedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderStatusChangedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse

interface ReminderHandler {
    fun handleReminderCreatedEventRequest(alexaRequest: AlexaRequest<ReminderCreatedEventRequest>): AlexaResponse

    fun handleReminderDeletedEventRequest(alexaRequest: AlexaRequest<ReminderDeletedEventRequest>): AlexaResponse

    fun handleReminderStartedEventRequest(alexaRequest: AlexaRequest<ReminderStartedEventRequest>): AlexaResponse

    fun handleReminderStatusChangedEventRequest(
        alexaRequest: AlexaRequest<ReminderStatusChangedEventRequest>
    ): AlexaResponse

    fun handleReminderUpdatedEventRequest(alexaRequest: AlexaRequest<ReminderUpdatedEventRequest>): AlexaResponse
}
