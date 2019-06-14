/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class MediaTypes {
    BOOK, EPISODE, ALBUM, SINGLE, MOVIE, GAME;

    companion object {

        @JsonCreator
        fun fromValue(text: String): MediaTypes? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
