package com.hp.kalexa.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.directive.Target
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.json.PayloadDeserializer
import com.hp.kalexa.model.payload.Payload
import java.time.LocalDateTime


class LinkResultRequest(
        requestId: String,
        locale: String,
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        timestamp: LocalDateTime,
        val target: Target,
        @JsonDeserialize(using = PayloadDeserializer::class)
        val payload: Payload<*>?,
        val status: String?,
        val linkStatus: LinkStatus?,
        val token: String,
//        @JsonDeserialize(using = TargetURIDeserializer::class)
        val targetURI: String?) : Request(requestId, locale, timestamp)