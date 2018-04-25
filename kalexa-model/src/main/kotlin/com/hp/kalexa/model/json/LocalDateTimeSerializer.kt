package com.hp.kalexa.model.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {

    override fun serialize(value: LocalDateTime, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(value.toInstant(ZoneOffset.UTC).toString())
    }
}