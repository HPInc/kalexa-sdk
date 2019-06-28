/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

data class Pattern(
    var gadgetIds: List<String> = emptyList(),
    var colors: List<String> = emptyList(),
    var action: InputEventActionType? = null,
    var repeat: Int? = null
)
