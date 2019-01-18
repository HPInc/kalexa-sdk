package com.hp.kalexa.model.entityresolution

import com.fasterxml.jackson.annotation.JsonCreator

enum class StatusCode(private val value: String) {

    ER_SUCCESS_MATCH("ER_SUCCESS_MATCH"),
    ER_SUCCESS_NO_MATCH("ER_SUCCESS_NO_MATCH"),
    ER_ERROR_TIMEOUT("ER_ERROR_TIMEOUT"),
    ER_ERROR_EXCEPTION("ER_ERROR_EXCEPTION");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): StatusCode? {
            for (statusCode in StatusCode.values()) {
                if (statusCode.value == text) {
                    return statusCode
                }
            }
            return null
        }
    }
}

