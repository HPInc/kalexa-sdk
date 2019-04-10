/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.directive.PlayDirective
import com.hp.kalexa.model.interfaces.audioplayer.AudioItem
import com.hp.kalexa.model.interfaces.audioplayer.PlayBehavior

class PlayDirectiveBuilder {
    var playBehavior: PlayBehavior? = null
    private var audioItem: AudioItem? = null

    fun audioItem(block: AudioItemBuilder.() -> Unit) {
        audioItem = AudioItemBuilder().apply { block() }.build()
    }

    fun build() = PlayDirective(playBehavior, audioItem)
}
