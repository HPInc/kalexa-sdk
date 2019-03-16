/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

data class TextContent(
    var primaryText: TextField? = null,
    var secondaryText: TextField? = null,
    var tertiaryText: TextField? = null
)
