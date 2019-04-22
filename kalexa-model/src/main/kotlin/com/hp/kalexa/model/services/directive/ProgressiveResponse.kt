/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.directive

import com.fasterxml.jackson.annotation.JsonProperty
import com.hp.kalexa.model.directive.VoicePlayerSpeakDirective

data class ProgressiveResponse(
    @JsonProperty("header")
    val header: Header,
    @JsonProperty("directive")
    val directive: VoicePlayerSpeakDirective
)