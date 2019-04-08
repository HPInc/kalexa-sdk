/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.interfaces.display.Image
import com.hp.kalexa.model.interfaces.display.ImageInstance
import com.hp.kalexa.model.interfaces.display.ImageSize
import com.hp.kalexa.model.interfaces.display.PlainText
import com.hp.kalexa.model.interfaces.display.RichText
import com.hp.kalexa.model.interfaces.display.TextContent
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.Response
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class AlexaDSLResponseBuilder {
    private var sessionAttributes: Map<String, Any?> = emptyMap()
    var version = "1.0"
    private var response: Response = Response()

    fun build(): AlexaResponse {
        return AlexaResponse(response, sessionAttributes, version)
    }

    fun sessionAttributes(block: () -> Map<String, Any?>) {
        sessionAttributes = block()
    }

    fun response(block: ResponseBuilder.() -> Unit) {
        response = ResponseBuilder().apply(block).build()
    }
}

fun alexaResponse(block: AlexaDSLResponseBuilder.() -> Unit): AlexaResponse =
    AlexaDSLResponseBuilder().apply(block).build()

fun textContent(block: (TextContent.() -> Unit)) = TextContent().apply(block)
fun plainText(block: (PlainText.() -> String)) = PlainText().apply { text = block() }
fun richText(block: (RichText.() -> String)) = RichText().apply { text = block() }
fun image(block: (ImageBuilder.() -> Unit)): Image = ImageBuilder().apply { block() }.build()
fun imageInstance(block: ImageInstanceBuilder.() -> Unit): ImageInstance =
    ImageInstanceBuilder().apply { block() }.build()
