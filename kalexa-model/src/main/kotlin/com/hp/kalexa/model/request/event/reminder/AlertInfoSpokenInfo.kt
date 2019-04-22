/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

data class AlertInfoSpokenInfo(
    var content: List<SpokenText> = emptyList()
)
