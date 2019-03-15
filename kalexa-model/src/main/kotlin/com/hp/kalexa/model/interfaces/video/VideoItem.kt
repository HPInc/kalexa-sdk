/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.video

data class VideoItem(
    var source: String = "",
    var metadata: Metadata? = null
)
