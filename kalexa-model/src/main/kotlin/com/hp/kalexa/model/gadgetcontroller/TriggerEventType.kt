/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gadgetcontroller

import com.fasterxml.jackson.annotation.JsonCreator

enum class TriggerEventType(val value: String) {
    BUTTONDOWN("buttonDown"),
    BUTTONUP("buttonUp"),
    NONE("none");

    override fun toString(): String {
        return this.value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): TriggerEventType? {
            for (value in values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
