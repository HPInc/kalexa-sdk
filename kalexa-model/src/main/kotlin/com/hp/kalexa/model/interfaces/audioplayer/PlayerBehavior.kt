/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.audioplayer

import com.fasterxml.jackson.annotation.JsonCreator

enum class PlayBehavior(private val value: String) {

    ENQUEUE("ENQUEUE"),
    REPLACE_ALL("REPLACE_ALL"),
    REPLACE_ENQUEUED("REPLACE_ENQUEUED");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): PlayBehavior? {
            for (playBehavior in PlayBehavior.values()) {
                if (playBehavior.value == text) {
                    return playBehavior
                }
            }
            return null
        }
    }
}

