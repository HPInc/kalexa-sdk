package com.hp.kalexa.model.payload.print

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.payload.Context
import com.hp.kalexa.model.payload.Payload

@JsonTypeName("PrintImageRequest")
class PrintImageRequest @JvmOverloads constructor(
        version: String = "1",
        language: Language = Language.EN_US,
        context: Context? = null,
        var title: String,
        var description: String? = null,
        var imageType: ImageType,
        var url: String) : Payload(version, language, context) {

    enum class ImageType {
        JPG, JPEG, TIFF, TIF, PNG
    }
}