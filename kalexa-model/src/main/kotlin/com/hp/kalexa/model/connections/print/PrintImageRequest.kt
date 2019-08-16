/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.connections.print

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.connections.Context
import com.hp.kalexa.model.connections.Input

@JsonTypeName("PrintImageRequest")
class PrintImageRequest @JvmOverloads constructor(
    version: String = "1",
    context: Context? = null,
    var title: String,
    var description: String? = null,
    var imageType: ImageType,
    var url: String
) : Input(version, context) {

    enum class ImageType {
        JPG, JPEG, TIFF, TIF, PNG
    }
}
