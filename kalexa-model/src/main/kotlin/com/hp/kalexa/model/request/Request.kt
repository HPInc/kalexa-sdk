/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = SessionStartedRequest::class, name = "SessionStartedRequest"),
    JsonSubTypes.Type(value = SessionEndedRequest::class, name = "SessionEndedRequest"),
    JsonSubTypes.Type(value = ConnectionsResponseRequest::class, name = "Connections.Response"),
    JsonSubTypes.Type(value = ConnectionsRequest::class, name = "Connections.Request"),
    JsonSubTypes.Type(value = ElementSelectedRequest::class, name = "Display.ElementSelected"),
    JsonSubTypes.Type(value = LaunchRequest::class, name = "LaunchRequest"),
    // handles all List Events
    JsonSubTypes.Type(value = EventRequest::class),
    // handles IntentRequest and CanFulfillIntentRequest
    JsonSubTypes.Type(value = BaseIntentRequest::class)
)
abstract class Request(
    val requestId: String,
    val locale: String,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val timestamp: LocalDateTime
)
