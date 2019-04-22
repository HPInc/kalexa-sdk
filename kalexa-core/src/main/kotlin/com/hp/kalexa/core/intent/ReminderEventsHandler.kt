/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.event.reminder.ReminderCreatedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderDeletedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderStartedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderStatusChangedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse

interface ReminderEventsHandler : BaseHandler {

    fun onReminderCreatedEventRequest(alexaRequest: AlexaRequest<ReminderCreatedEventRequest>): AlexaResponse

    fun onReminderUpdatedEventRequest(alexaRequest: AlexaRequest<ReminderUpdatedEventRequest>): AlexaResponse

    fun onReminderDeletedEventRequest(alexaRequest: AlexaRequest<ReminderDeletedEventRequest>): AlexaResponse

    fun onReminderStartedEventRequest(alexaRequest: AlexaRequest<ReminderStartedEventRequest>): AlexaResponse

    fun onReminderStatusChangedEventRequest(
        alexaRequest: AlexaRequest<ReminderStatusChangedEventRequest>
    ): AlexaResponse
}
