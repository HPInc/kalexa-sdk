/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.request.Request
import java.time.LocalDateTime

@JsonTypeName("Reminders.ReminderDeleted")
class ReminderDeletedEventRequest(
    requestId: String,
    locale: String,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    var body: ReminderDeletedEvent? = null
) : Request(requestId, locale, timestamp)
