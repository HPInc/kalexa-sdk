/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.canfulfill

data class CanFulfillSlot(
    val canUnderstand: CanUnderstandSlotValues? = null,
    val canFulfill: CanFulfillSlotValues? = null
)
