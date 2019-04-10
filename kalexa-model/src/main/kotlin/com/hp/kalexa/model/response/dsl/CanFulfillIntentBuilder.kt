/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.canfulfill.CanFulfillIntent
import com.hp.kalexa.model.canfulfill.CanFulfillIntentValues
import com.hp.kalexa.model.canfulfill.CanFulfillSlot
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class CanFulfillIntentBuilder {
    var canFulfill: CanFulfillIntentValues? = null
    private var slots: Map<String, CanFulfillSlot> = mutableMapOf()

    fun slots(block: SlotsBuilder.() -> Unit) {
        slots = SlotsBuilder().apply { block() }
    }

    fun build(): CanFulfillIntent = CanFulfillIntent(canFulfill, slots)
}
