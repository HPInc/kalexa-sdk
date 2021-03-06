/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.skillevents

data class ProactiveSubscriptionChangedBody(
    val subscriptions: List<ProactiveSubscriptionEvent> = emptyList()
)
