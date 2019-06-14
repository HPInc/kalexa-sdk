/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class MessageStatus {
    UNREAD, FLAGGED;

    companion object {

        @JsonCreator
        fun fromValue(text: String): MessageStatus? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
