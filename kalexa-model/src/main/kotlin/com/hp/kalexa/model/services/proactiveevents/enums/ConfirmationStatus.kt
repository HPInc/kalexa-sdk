/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class ConfirmationStatus {
    CONFIRMED, CANCELED, RESCHEDULED, REQUESTED, CREATED, UPDATED;

    companion object {

        @JsonCreator
        fun fromValue(text: String): ConfirmationStatus? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
