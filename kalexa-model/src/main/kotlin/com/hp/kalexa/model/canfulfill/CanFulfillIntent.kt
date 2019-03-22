/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.canfulfill

data class CanFulfillIntent(
    val canFulfill: CanFulfillIntentValues? = null,
    val slots: Map<String, CanFulfillSlot>? = mutableMapOf()
)
