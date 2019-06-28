/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents

import com.fasterxml.jackson.annotation.JsonCreator

enum class SkillStage {
    DEVELOPMENT,
    LIVE;

    companion object {

        @JsonCreator
        fun fromValue(text: String): SkillStage? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
