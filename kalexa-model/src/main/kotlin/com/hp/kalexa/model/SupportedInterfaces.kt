/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.hp.kalexa.model.interfaces.audioplayer.AudioPlayer
import com.hp.kalexa.model.interfaces.display.Display
import com.hp.kalexa.model.interfaces.geolocation.Geolocation
import com.hp.kalexa.model.interfaces.video.VideoApp

data class SupportedInterfaces(
    @JsonProperty("AudioPlayer")
    val audioPlayer: AudioPlayer? = null,
    @JsonProperty("Display")
    val display: Display? = null,
    @JsonProperty("VideoApp")
    val videoApp: VideoApp? = null,
    @JsonProperty("Geolocation")
    val geolocation: Geolocation? = null
)
