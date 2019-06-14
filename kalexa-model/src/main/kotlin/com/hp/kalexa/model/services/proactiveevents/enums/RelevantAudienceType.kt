/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class RelevantAudienceType(val value: String) {
    UNICAST("Unicast"),
    MULTICAST("Multicast");

    override fun toString(): String {
        return this.value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): RelevantAudienceType? {
            for (value in values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
