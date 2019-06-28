/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.skillevents

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.request.EventRequest
import java.time.LocalDateTime

@JsonTypeName("AlexaSkillEvent.SkillAccountLinked")
class AccountLinkedRequest(
    requestId: String,
    locale: String = "",
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    eventCreationTime: LocalDateTime,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    eventPublishingTime: LocalDateTime,
    var body: AccountLinkedBody
) : EventRequest(requestId, locale, timestamp, eventCreationTime, eventPublishingTime)
