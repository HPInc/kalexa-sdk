/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

import java.time.LocalDateTime

data class Trigger(
    var type: TriggerType? = null,
    var scheduledTime: LocalDateTime? = null,
    var offsetInSeconds: Int? = null,
    var timeZoneId: String? = null,
    var recurrence: Recurrence? = null
)
