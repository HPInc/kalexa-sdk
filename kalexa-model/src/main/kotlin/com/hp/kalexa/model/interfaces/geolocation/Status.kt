/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.geolocation

import com.fasterxml.jackson.annotation.JsonCreator

enum class Status(private val value: String) {

    RUNNING("RUNNING"),
    STOPPED("STOPPED");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): Status? {
            for (status in Status.values()) {
                if (status.value == text) {
                    return status
                }
            }
            return null
        }
    }
}
