/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.interfaces.display.Image
import com.hp.kalexa.model.interfaces.display.ImageInstance
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class ImageBuilder {
    var contentDescription: String = ""
    var sources: List<ImageInstance> = emptyList()

    fun build() = Image(contentDescription, sources)
}
