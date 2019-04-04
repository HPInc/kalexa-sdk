package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.interfaces.display.ImageInstance
import com.hp.kalexa.model.interfaces.display.ImageSize
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class ImageInstanceBuilder {
    var url: String = ""
    var size: ImageSize = ImageSize.X_SMALL
    var widthPixels: Int? = null
    var heightPixels: Int? = null

    fun build() = ImageInstance(url, size, widthPixels, heightPixels)
}
