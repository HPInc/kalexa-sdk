/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.canfulfill

import com.fasterxml.jackson.annotation.JsonCreator

enum class CanFulfillIntentValues(val value: String) {

    YES("YES"),
    NO("NO"),
    MAYBE("MAYBE");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): CanFulfillIntentValues? {
            for (value in CanFulfillIntentValues.values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
