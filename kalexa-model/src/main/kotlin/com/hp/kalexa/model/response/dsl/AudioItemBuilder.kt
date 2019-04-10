/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.interfaces.audioplayer.AudioItem
import com.hp.kalexa.model.interfaces.audioplayer.Stream

class AudioItemBuilder {
    private var stream: Stream = Stream()

    fun stream(block: StreamBuilder.() -> Unit) {
        stream = StreamBuilder().apply { block() }.build()
    }

    fun build() = AudioItem(stream)
}
