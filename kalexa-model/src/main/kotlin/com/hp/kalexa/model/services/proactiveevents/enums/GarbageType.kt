/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class GarbageType {
    BOTTLES, BULKY, BURNABLE, CANS, CLOTHING, COMPOSTABLE, CRUSHABLE, GARDEN_WASTE, GLASS, HAZARDOUS,
    HOME_APPLIANCES, KITCHEN_WASTE, LANDFILL, PET_BOTTLES, RECYCLABLE_PLASTICS, WASTE_PAPE;

    companion object {

        @JsonCreator
        fun fromValue(text: String): GarbageType? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
