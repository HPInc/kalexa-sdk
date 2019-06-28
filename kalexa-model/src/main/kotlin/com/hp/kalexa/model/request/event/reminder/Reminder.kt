/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

import java.time.OffsetDateTime

data class Reminder(
    var alertToken: String? = null,
    var createdTime: OffsetDateTime? = null,
    var updatedTime: OffsetDateTime? = null,
    var status: Status? = null,
    var trigger: Trigger? = null,
    var alertInfo: AlertInfo? = null,
    var pushNotification: PushNotification? = null,
    var version: String? = null
)
