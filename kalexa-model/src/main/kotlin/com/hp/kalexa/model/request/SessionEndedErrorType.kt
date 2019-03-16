/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonCreator

enum class SessionEndedErrorType(private val value: String) {

    INVALID_RESPONSE("INVALID_RESPONSE"),
    DEVICE_COMMUNICATION_ERROR("DEVICE_COMMUNICATION_ERROR"),
    INTERNAL_SERVICE_ERROR("INTERNAL_SERVICE_ERROR");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): SessionEndedErrorType? {
            for (sessionEndedErrorType in SessionEndedErrorType.values()) {
                if (sessionEndedErrorType.value == text) {
                    return sessionEndedErrorType
                }
            }
            return null
        }
    }
}
