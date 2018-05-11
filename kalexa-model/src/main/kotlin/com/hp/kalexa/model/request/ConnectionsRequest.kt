package com.hp.kalexa.model.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.json.PayloadDeserializer
import com.hp.kalexa.model.payload.Payload
import java.time.LocalDateTime

class ConnectionsRequest(
        requestId: String,
        locale: String,
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        timestamp: LocalDateTime,
        val name: String,
        @JsonDeserialize(using = PayloadDeserializer::class)
        val payload: Payload<*>) : Request(requestId, locale, timestamp)