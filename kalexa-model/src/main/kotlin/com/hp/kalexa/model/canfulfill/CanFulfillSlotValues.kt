/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.canfulfill

import com.fasterxml.jackson.annotation.JsonCreator

enum class CanFulfillSlotValues(val value: String) {
    YES("YES"),
    NO("NO");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): CanFulfillSlotValues? {
            for (value in CanFulfillSlotValues.values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
