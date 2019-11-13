/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.connections

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class UriType(private val value: String) {
    PRINT_WEB_PAGE_VERSION_1("connection://AMAZON.PrintWebPage/1"),
    PRINT_IMAGE_VERSION_1("connection://AMAZON.PrintImage/1"),
    PRINT_PDF_VERSION_1("connection://AMAZON.PrintPDF/1"),
    PRINT_SCHEDULE_TAXI_RESERVATION_VERSION_1("connection://AMAZON.ScheduleTaxiReservation/1"),
    SCHEDULE_FOOD_ESTABLISHMENT_RESERVATION_VERSION_1("connection://AMAZON.ScheduleFoodEstablishmentReservation/1");

    @JsonValue
    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): UriType? {
            for (name in values()) {
                if (name.value == text) {
                    return name
                }
            }
            return null
        }
    }
}
