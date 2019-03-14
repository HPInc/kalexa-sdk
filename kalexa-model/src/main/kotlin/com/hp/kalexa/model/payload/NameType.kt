/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.payload

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

enum class NameType(private val value: String) {
    @JsonProperty("Print")
    PRINT("Print"),
    @JsonProperty("Log")
    LOG("Log"),
    @JsonProperty("Schedule")
    SCHEDULE("Schedule");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): NameType? {
            for (name in NameType.values()) {
                if (name.value == text) {
                    return name
                }
            }
            return null
        }
    }
}