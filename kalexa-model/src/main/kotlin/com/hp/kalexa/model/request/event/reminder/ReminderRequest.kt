/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

import java.time.OffsetDateTime

data class ReminderRequest(
    var requestTime: OffsetDateTime? = null,
    var trigger: Trigger? = null,
    var alertInfo: AlertInfo? = null,
    var pushNotification: PushNotification? = null
)
