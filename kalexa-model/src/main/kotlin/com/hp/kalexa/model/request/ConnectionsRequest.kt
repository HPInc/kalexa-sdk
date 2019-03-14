/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.payload.NameType
import java.time.LocalDateTime

class ConnectionsRequest(
        requestId: String,
        locale: String,
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        timestamp: LocalDateTime,
        val name: NameType,
        val payload: Map<String, Any>) : Request(requestId, locale, timestamp)