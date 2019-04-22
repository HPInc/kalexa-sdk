/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

data class InputHandlerEvent(
    var name: String? = null,
    var inputEvents: List<InputEvent> = emptyList()
)
