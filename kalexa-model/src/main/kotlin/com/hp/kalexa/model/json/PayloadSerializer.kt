package com.hp.kalexa.model.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.hp.kalexa.model.payload.Payload

class PayloadSerializer : JsonSerializer<Payload<*>>() {
    override fun serialize(value: Payload<*>, gen: JsonGenerator, serializers: SerializerProvider?) {
        val type = value.getObject()!!::class.java
        gen.writeStartObject()
        gen.writeStringField("type", value.type)
        gen.writeStringField("version", value.version)
        gen.writeObjectField(type.simpleName, value.getObject())
        gen.writeEndObject()
    }
}