/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.viewport

import com.fasterxml.jackson.annotation.JsonCreator

enum class Touch(val value: String) {
    SINGLE("SINGLE");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): Touch? {
            for (value in values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
