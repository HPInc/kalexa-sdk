package com.hp.kalexa.model.payload.print

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

enum class Language(private val value: String) {
    @JsonProperty("en-US")
    EN_US("en-US");

    override fun toString(): String {
        return value
    }

    companion object {
        @JsonCreator
        fun fromValue(text: String): Language? {
            for (language in Language.values()) {
                if (language.name == text) {
                    return language
                }
            }
            return null
        }
    }
}