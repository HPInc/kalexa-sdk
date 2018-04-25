package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SupportedInterfaces(
        @JsonProperty("AudioPlayer")
        val audioPlayer: AudioPlayer? = null,
        @JsonProperty("Display")
        val display: Display? = null,
        @JsonProperty("VideoApp")
        val videoApp: VideoApp? = null
)
