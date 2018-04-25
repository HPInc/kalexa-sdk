package com.hp.kalexa.model.interfaces.display

data class ImageInstance(
        val url: String = "",
        val size: ImageSize = ImageSize.SMALL,
        val widthPixels: Int = 0,
        val heightPixels: Int = 0)