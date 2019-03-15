/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.audioplayer

import com.fasterxml.jackson.annotation.JsonCreator

enum class ClearBehavior(private val value: String) {

    CLEAR_ALL("CLEAR_ALL"),
    CLEAR_ENQUEUED("CLEAR_ENQUEUED");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): ClearBehavior? {
            for (clearBehavior in ClearBehavior.values()) {
                if (clearBehavior.value == text) {
                    return clearBehavior
                }
            }
            return null
        }
    }
}
