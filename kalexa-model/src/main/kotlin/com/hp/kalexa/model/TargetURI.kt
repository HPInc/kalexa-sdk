package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonValue

enum class TargetURI(private val action: String) {
    SCHEDULE("conn://*/Schedule"),
    LOG("conn://*/Log"),
    PRINT("conn://*/Print");

    @JsonValue
    override fun toString(): String {
        return action
    }
}