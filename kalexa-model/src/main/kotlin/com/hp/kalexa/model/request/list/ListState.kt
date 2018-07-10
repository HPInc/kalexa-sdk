package com.hp.kalexa.model.request.list

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

enum class ListState(private val value: String) {
    @JsonProperty("active")
    ACTIVE("active"),
    @JsonProperty("archived")
    ARCHIVED("archived");

    override fun toString(): String {
        return this.value
    }

    companion object {
        @JsonCreator
        fun fromValue(text: String): ListState? {
            for (listState in ListState.values()) {
                if (listState.value == text) {
                    return listState
                }
            }
            return null
        }
    }
}
