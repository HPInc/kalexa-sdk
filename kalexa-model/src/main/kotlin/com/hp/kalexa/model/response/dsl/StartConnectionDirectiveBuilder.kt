/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.directive.StartConnectionDirective
import com.hp.kalexa.model.payload.NameType
import com.hp.kalexa.model.payload.Input
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class SendRequestDirectiveBuilder {
    lateinit var name: NameType
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

    fun payload(block: SendRequestDirectiveBuilder.() -> Input) {
        apply { input = block() }
    }

    fun build(): StartConnectionDirective = StartConnectionDirective(name, input, token)
}
