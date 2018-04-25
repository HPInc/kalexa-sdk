package com.hp.kalexa.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

class IntentRequest(
        requestId: String,
        locale: String,
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        timestamp: LocalDateTime,
        val intent: Intent,
        val dialogState: DialogState?) : Request(requestId, locale, timestamp)

