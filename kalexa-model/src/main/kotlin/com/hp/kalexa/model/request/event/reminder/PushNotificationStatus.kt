/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

import com.fasterxml.jackson.annotation.JsonCreator

enum class PushNotificationStatus(val value: String) {
    ENABLED("ENABLED"),
    DISABLED("DISABLED");

    override fun toString(): String {
        return this.value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): PushNotificationStatus? {
            for (value in values()) {
                if (value.value == text) {
                    return value
                }
            }
            return null
        }
    }
}
