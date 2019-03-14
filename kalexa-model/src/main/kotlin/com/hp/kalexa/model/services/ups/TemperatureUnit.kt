/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.ups

import com.fasterxml.jackson.annotation.JsonCreator

enum class TemperatureUnit(private val value: String) {

    CELSIUS("CELSIUS"),

    FAHRENHEIT("FAHRENHEIT");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): TemperatureUnit? {
            for (temperatureUnit in TemperatureUnit.values()) {
                if (temperatureUnit.value == text) {
                    return temperatureUnit
                }
            }
            return null
        }
    }
}