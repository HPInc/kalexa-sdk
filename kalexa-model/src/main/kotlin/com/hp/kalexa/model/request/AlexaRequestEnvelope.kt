package com.hp.kalexa.model.request

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Request
import com.hp.kalexa.model.Session


class AlexaRequestEnvelope<out T : Request>(
        val version: String,
        val session: Session,
        val context: Context,
        val request: T) {

    companion object {
        val OBJECT_MAPPER = jacksonObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)

        fun fromJson(json: String): AlexaRequestEnvelope<*> {
            return OBJECT_MAPPER.readValue(json, AlexaRequestEnvelope::class.java)
        }

        fun fromJson(json: ByteArray): AlexaRequestEnvelope<*> {
            return OBJECT_MAPPER.readValue(json, AlexaRequestEnvelope::class.java)
        }
    }
}