/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

data class Event(
    var shouldEndInputHandler: Boolean? = null,
    var meets: List<String> = emptyList(),
    var fails: List<String>? = emptyList(),
    var reports: EventReportingType? = null,
    var maximumInvocations: Int? = null,
    var triggerTimeMilliseconds: Long? = null
)
