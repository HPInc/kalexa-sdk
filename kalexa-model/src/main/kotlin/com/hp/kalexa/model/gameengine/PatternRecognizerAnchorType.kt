/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

import com.fasterxml.jackson.annotation.JsonCreator

enum class PatternRecognizerAnchorType(val value: String) {
    START("start"),
    END("end"),
    ANYWHERE("anywhere");

    override fun toString(): String {
        return this.value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): PatternRecognizerAnchorType? {
            for (value in values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
