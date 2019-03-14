/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.list

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

enum class ListItemState(private val value: String) {
    @JsonProperty("active")
    ACTIVE("active"),
    @JsonProperty("completed")
    COMPLETED("completed");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): ListItemState? {
            for (listItemState in ListItemState.values()) {
                if (listItemState.value == text) {
                    return listItemState
                }
            }
            return null
        }
    }
}

