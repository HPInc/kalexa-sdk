/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

data class InputEvent(
    var gadgetId: String? = null,
    var timestamp: String? = null,
    var action: InputEventActionType? = null,
    var color: String? = null,
    var feature: String? = null
)
