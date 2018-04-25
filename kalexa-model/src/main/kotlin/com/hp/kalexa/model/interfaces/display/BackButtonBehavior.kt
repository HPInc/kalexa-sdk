package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonCreator

enum class BackButtonBehavior(private val value: String) {

    HIDDEN("HIDDEN"),
    VISIBLE("VISIBLE");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): BackButtonBehavior? {
            for (backButtonBehavior in BackButtonBehavior.values()) {
                if (backButtonBehavior.value == text) {
                    return backButtonBehavior
                }
            }
            return null
        }
    }
}

