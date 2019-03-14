/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonCreator

enum class DialogState(private val value: String) {

    STARTED("STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): DialogState? {
            for (dialogState in DialogState.values()) {
                if (dialogState.value == text) {
                    return dialogState
                }
            }
            return null
        }
    }
}

