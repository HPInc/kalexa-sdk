/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.ups

import com.fasterxml.jackson.annotation.JsonCreator


enum class DistanceUnit(private val value: String) {

    METRIC("METRIC"),

    IMPERIAL("IMPERIAL");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): DistanceUnit? {
            for (distanceUnit in DistanceUnit.values()) {
                if (distanceUnit.value == text) {
                    return distanceUnit
                }
            }
            return null
        }
    }
}