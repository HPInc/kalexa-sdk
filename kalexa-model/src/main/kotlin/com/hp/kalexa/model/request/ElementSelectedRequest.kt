package com.hp.kalexa.model.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

class ElementSelectedRequest(
        requestId: String,
        locale: String,
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        timestamp: LocalDateTime,
        val token: String) : Request(requestId, locale, timestamp)