/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.hp.kalexa.model.interfaces.audioplayer.AudioPlayer
import com.hp.kalexa.model.interfaces.display.Display
import com.hp.kalexa.model.interfaces.geolocation.Geolocation

data class Context(
        @JsonProperty("System")
        val system: System,
        @JsonProperty("AudioPlayer")
        val audioPlayer: AudioPlayer? = null,
        @JsonProperty("Display")
        val display: Display? = null,
        @JsonProperty("Geolocation")
        val geolocation: Geolocation? = null) {

    /**
     * Verifies whether the device has display or not.
     * @return true if device has display interface or false otherwise.
     */
    fun hasDisplay(): Boolean {
        val supportedInterfaces = system.device?.supportedInterfaces
        val display = supportedInterfaces?.display
        return display?.templateVersion.equals("1.0") &&
                display?.markupVersion.equals("1.0")
    }

}