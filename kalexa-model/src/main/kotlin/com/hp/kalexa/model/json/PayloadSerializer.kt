package com.hp.kalexa.model.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.hp.kalexa.model.payload.Payload

class PayloadSerializer : JsonSerializer<Payload<*>>() {
    override fun serialize(value: Payload<*>, gen: JsonGenerator, serializers: SerializerProvider?) {
        val type = value.getType()!!::class.java
        gen.writeStartObject()
        gen.writeObjectField(type.simpleName, value.getType())
        gen.writeEndObject()
    }
}