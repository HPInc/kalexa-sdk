/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.DialogState
import com.hp.kalexa.model.Intent
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = IntentRequest::class, name = "IntentRequest"),
    JsonSubTypes.Type(value = CanFulfillIntentRequest::class, name = "CanFulfillIntentRequest")
)
abstract class BaseIntentRequest(
    requestId: String,
    locale: String,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    originIpAddress: String?,
    val intent: Intent,
    val dialogState: DialogState? = null
) : Request(requestId, locale, timestamp, originIpAddress)
