/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.DialogState
import com.hp.kalexa.model.Intent
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonTypeName("CanFulfillIntentRequest")
class CanFulfillIntentRequest(
    requestId: String,
    locale: String,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    originIpAddress: String?,
    intent: Intent,
    dialogState: DialogState?
) : BaseIntentRequest(requestId, locale, timestamp, originIpAddress, intent, dialogState)
