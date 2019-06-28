/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class OccasionType {
    RESERVATION_REQUEST, RESERVATION, APPOINTMENT_REQUEST, APPOINTMENT;

    companion object {

        @JsonCreator
        fun fromValue(text: String): OccasionType? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
