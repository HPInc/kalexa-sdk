/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonCreator

enum class ImageSize(private val value: String) {

    X_SMALL("X_SMALL"),
    SMALL("SMALL"),
    MEDIUM("MEDIUM"),
    LARGE("LARGE"),
    X_LARGE("X_LARGE");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): ImageSize? {
            for (imageSize in ImageSize.values()) {
                if (imageSize.value == text) {
                    return imageSize
                }
            }
            return null
        }
    }
}
