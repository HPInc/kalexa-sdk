package com.hp.kalexa.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

class SessionEndedRequest(
        requestId: String,
        locale: String,
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        timestamp: LocalDateTime,
        val reason: SessionEndedReason?,
        val error: SessionEndedError?) : Request(requestId, locale, timestamp)