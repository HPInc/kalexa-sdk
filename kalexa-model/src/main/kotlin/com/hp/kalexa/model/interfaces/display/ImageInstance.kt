/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

data class ImageInstance(
        val url: String,
        val size: ImageSize = ImageSize.X_SMALL,
        val widthPixels: Int? = null,
        val heightPixels: Int? = null)