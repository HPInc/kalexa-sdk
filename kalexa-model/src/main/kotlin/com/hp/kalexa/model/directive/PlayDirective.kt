package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.directive.Directive
import com.hp.kalexa.model.interfaces.audioplayer.AudioItem
import com.hp.kalexa.model.interfaces.audioplayer.PlayBehavior


@JsonTypeName("AudioPlayer.Play")
data class PlayDirective(
        val playBehavior: PlayBehavior? = null,
        val audioItem: AudioItem? = null) : Directive()