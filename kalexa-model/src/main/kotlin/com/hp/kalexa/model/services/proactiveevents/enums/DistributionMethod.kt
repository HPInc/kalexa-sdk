/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class DistributionMethod {
    STREAM, AIR, RELEASE, PREMIERE, DROP;

    companion object {

        @JsonCreator
        fun fromValue(text: String): DistributionMethod? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
