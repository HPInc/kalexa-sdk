/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
        val readValueAs = p.readValueAs(String::class.java)
        val instant = Instant.from(dateTimeFormatter.parse(readValueAs))
        return LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.id))
    }
}
