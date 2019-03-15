/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonCreator

enum class PermissionStatus(private val value: String) {

    GRANTED("GRANTED"),
    DENIED("DENIED");

    override fun toString(): String {
        return value
    }

    companion object {

        @JsonCreator
        fun fromValue(text: String): PermissionStatus? {
            for (permissionStatus in PermissionStatus.values()) {
                if (permissionStatus.value == text) {
                    return permissionStatus
                }
            }
            return null
        }
    }
}