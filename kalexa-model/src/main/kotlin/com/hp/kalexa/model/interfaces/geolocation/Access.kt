/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.geolocation

import com.fasterxml.jackson.annotation.JsonCreator

enum class Access(private val value: String) {

    ENABLED("ENABLED"),
    DISABLED("DISABLED"),
    UNKNOWN("UNKNOWN");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): Access? {
            for (access in Access.values()) {
                if (access.value == text) {
                    return access
                }
            }
            return null
        }
    }
}