/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

data class GetRemindersResponse(
    var totalCount: String? = null,
    var alerts: List<Reminder> = emptyList(),
    var links: String? = null
)
