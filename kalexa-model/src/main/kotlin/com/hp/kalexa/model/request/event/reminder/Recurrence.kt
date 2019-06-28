/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

data class Recurrence(
    var freq: RecurrenceFreq? = null,
    var byDay: List<RecurrenceDay> = emptyList(),
    var interval: Int? = null
)
