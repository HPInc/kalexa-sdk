/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.DialogState
import com.hp.kalexa.model.Intent
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import java.time.LocalDateTime

class IntentRequest(
        requestId: String,
        locale: String,
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        timestamp: LocalDateTime,
        val intent: Intent,
        val dialogState: DialogState?) : Request(requestId, locale, timestamp)

