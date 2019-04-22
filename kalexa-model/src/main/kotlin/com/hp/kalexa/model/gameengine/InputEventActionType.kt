/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

import com.fasterxml.jackson.annotation.JsonCreator

enum class InputEventActionType(val value: String) {
    DOWN("down"),
    UP("up");

    override fun toString(): String {
        return this.value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): InputEventActionType? {
            for (value in values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
