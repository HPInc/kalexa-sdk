/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.request.EventRequest
import com.hp.kalexa.model.request.list.ListItemBody
import java.time.LocalDateTime

@JsonTypeName("AlexaHouseholdListEvent.ItemsDeleted")
class ListItemsDeletedEventRequest(
    requestId: String,
    locale: String = "",
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    eventCreationTime: LocalDateTime,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    eventPublishingTime: LocalDateTime,
    var body: ListItemBody
) : EventRequest(requestId, locale, timestamp, eventCreationTime, eventPublishingTime)