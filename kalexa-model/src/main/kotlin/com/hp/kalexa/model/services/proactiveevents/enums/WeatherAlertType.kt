/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class WeatherAlertType {
    TORNADO, HURRICANE, SNOW_STORM, THUNDER_STORM;

    companion object {

        @JsonCreator
        fun fromValue(text: String): WeatherAlertType? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
