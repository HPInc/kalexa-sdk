/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

import com.fasterxml.jackson.annotation.JsonCreator

enum class RecurrenceDay(val value: String) {
    SU("SU"),
    MO("MO"),
    TU("TU"),
    WE("WE"),
    TH("TH"),
    FR("FR"),
    SA("SA");

    override fun toString(): String {
        return this.value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): RecurrenceDay? {
            for (value in values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
