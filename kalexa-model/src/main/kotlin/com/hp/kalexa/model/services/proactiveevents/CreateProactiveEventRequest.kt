/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents

import java.time.OffsetDateTime

data class CreateProactiveEventRequest(
    var timestamp: OffsetDateTime? = null,
    var referenceId: String? = null,
    var expiryTime: OffsetDateTime? = null,
    var event: Event? = null,
    var localizedAttributes: List<Any>? = null,
    var relevantAudience: RelevantAudience? = null
)
