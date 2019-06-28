/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.gameengine.InputHandlerEvent
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonTypeName("GameEngine.InputHandlerEvent")
class InputHandlerEventRequest(
    requestId: String,
    locale: String,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    val originatingRequestId: String,
    val events: List<InputHandlerEvent> = emptyList()
) : Request(requestId, locale, timestamp)
