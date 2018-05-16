package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonValue

@Deprecated("Not used anymore, use SendRequestDirective instead")
enum class TargetURI(private val action: String) {
    SCHEDULE("conn://*/Schedule"),
    LOG("conn://*/Log"),
    PRINT("conn://*/Print");

    @JsonValue
    override fun toString(): String {
        return action
    }
}