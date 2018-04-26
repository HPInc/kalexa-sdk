package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonCreator

enum class SessionEndedReason(private val value: String) {

    USER_INITIATED("USER_INITIATED"),
    ERROR("ERROR"),
    EXCEEDED_MAX_REPROMPTS("EXCEEDED_MAX_REPROMPTS");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): SessionEndedReason? {
            for (sessionEndedReason in SessionEndedReason.values()) {
                if (sessionEndedReason.value == text) {
                    return sessionEndedReason
                }
            }
            return null
        }
    }
}

