/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.directive.StartConnectionDirective
import com.hp.kalexa.model.connections.UriType
import com.hp.kalexa.model.connections.Input
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class StartConnectionDirectiveBuilder {
    lateinit var uri: UriType
    private lateinit var input: Input
    var token: String = "none"

    fun printPDFRequest(block: PrintPDFRequestBuilder.() -> Unit) {
        apply { input = PrintPDFRequestBuilder().apply { block() }.build() }
    }

    fun printWebPageRequest(block: PrintWebPageRequestBuilder.() -> Unit) {
        apply { input = PrintWebPageRequestBuilder().apply { block() }.build() }
    }

    fun printImageRequest(block: PrintImageRequestBuilder.() -> Unit) {
        apply { input = PrintImageRequestBuilder().apply { block() }.build() }
    }

    fun build(): StartConnectionDirective = StartConnectionDirective(uri, input, token)
}
