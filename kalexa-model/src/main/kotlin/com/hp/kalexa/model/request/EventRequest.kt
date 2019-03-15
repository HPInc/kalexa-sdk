/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import java.time.LocalDateTime

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(
        JsonSubTypes.Type(value = ListCreatedEventRequest::class, name = "AlexaHouseholdListEvent.ListCreated"),
        JsonSubTypes.Type(value = ListUpdatedEventRequest::class, name = "AlexaHouseholdListEvent.ListUpdated"),
        JsonSubTypes.Type(value = ListDeletedEventRequest::class, name = "AlexaHouseholdListEvent.ListDeleted"),
        JsonSubTypes.Type(value = ListItemsCreatedEventRequest::class, name = "AlexaHouseholdListEvent.ItemsCreated"),
        JsonSubTypes.Type(value = ListItemsUpdatedEventRequest::class, name = "AlexaHouseholdListEvent.ItemsUpdated"),
        JsonSubTypes.Type(value = ListItemsDeletedEventRequest::class, name = "AlexaHouseholdListEvent.ItemsDeleted"))
abstract class EventRequest(
    requestId: String,
    locale: String = "",
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val eventCreationTime: LocalDateTime,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val eventPublishingTime: LocalDateTime
) : Request(requestId, locale, timestamp)