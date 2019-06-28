/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

data class ReminderResponse(
    var alertToken: String? = null,
    var createdTime: String? = null,
    var updatedTime: String? = null,
    var status: Status? = null,
    var version: String? = null,
    var href: String? = null
)
