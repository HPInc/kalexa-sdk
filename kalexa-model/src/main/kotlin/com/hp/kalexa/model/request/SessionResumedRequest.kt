/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.connections.Cause
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonTypeName("SessionResumedRequest")
class SessionResumedRequest(
    requestId: String,
    locale: String,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    originIpAddress: String? = null,
    val cause: Cause
) : Request(requestId, locale, timestamp, originIpAddress)
