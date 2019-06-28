/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.gameengine.Event
import com.hp.kalexa.model.gameengine.Recognizer

@JsonTypeName("GameEngine.StartInputHandler")
data class StartInputHandlerDirective(
    var timeout: Long? = null,
    var proxies: List<String> = emptyList(),
    var recognizers: Map<String, Recognizer> = emptyMap(),
    var events: Map<String, Event>? = null
) : Directive()
