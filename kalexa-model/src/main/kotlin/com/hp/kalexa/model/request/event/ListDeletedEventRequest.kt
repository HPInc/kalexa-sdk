/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.request.EventRequest
import com.hp.kalexa.model.request.list.ListBody
import java.time.LocalDateTime

@JsonTypeName("AlexaHouseholdListEvent.ListDeleted")
class ListDeletedEventRequest(
    requestId: String,
    locale: String = "",
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    originIpAddress: String? = null,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    eventCreationTime: LocalDateTime,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    eventPublishingTime: LocalDateTime,
    var body: ListBody
) : EventRequest(requestId, locale, timestamp, originIpAddress, eventCreationTime, eventPublishingTime)
