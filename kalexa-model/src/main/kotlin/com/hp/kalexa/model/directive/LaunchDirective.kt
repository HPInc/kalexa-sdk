package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.Directive
import com.hp.kalexa.model.VideoItem

@JsonTypeName("VideoApp.Launch")
class LaunchDirective(var videoItem: VideoItem? = null) : Directive()

