/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents

import com.fasterxml.jackson.annotation.JsonCreator

enum class RelationshipToInvite {
    FRIEND, CONTACT;

    companion object {

        @JsonCreator
        fun fromValue(text: String): RelationshipToInvite? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
