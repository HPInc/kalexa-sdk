/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class OfferType {
    MATCH, REMATCH, GAME;

    companion object {

        @JsonCreator
        fun fromValue(text: String): OfferType? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
