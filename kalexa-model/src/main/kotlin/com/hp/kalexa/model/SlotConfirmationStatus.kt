/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonCreator

enum class SlotConfirmationStatus(private val value: String) {
    NONE("NONE"),
    DENIED("DENIED"),
    CONFIRMED("CONFIRMED");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): SlotConfirmationStatus? {
            for (slotConfirmationStatus in SlotConfirmationStatus.values()) {
                if (slotConfirmationStatus.value == text) {
                    return slotConfirmationStatus
                }
            }
            return null
        }
    }
}

