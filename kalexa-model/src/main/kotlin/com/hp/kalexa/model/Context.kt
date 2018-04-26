package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.hp.kalexa.model.interfaces.audioplayer.AudioPlayer
import com.hp.kalexa.model.interfaces.display.Display

data class Context(
        @JsonProperty("System")
        val system: System,
        @JsonProperty("AudioPlayer")
        val audioPlayer: AudioPlayer? = null,
        @JsonProperty("Display")
        val display: Display? = null)