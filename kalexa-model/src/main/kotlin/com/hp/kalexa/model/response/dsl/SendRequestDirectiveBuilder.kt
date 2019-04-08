package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.directive.SendRequestDirective
import com.hp.kalexa.model.payload.NameType
import com.hp.kalexa.model.payload.Payload
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class SendRequestDirectiveBuilder {
    lateinit var name: NameType
    private lateinit var payload: Payload
    var token: String = "none"

    fun printPDFRequest(block: PrintPDFRequestBuilder.() -> Unit) {
        apply { payload = PrintPDFRequestBuilder().apply { block() }.build() }
    }

    fun printWebPageRequest(block: PrintWebPageRequestBuilder.() -> Unit) {
        apply { payload = PrintWebPageRequestBuilder().apply { block() }.build() }
    }

    fun printImageRequest(block: PrintImageRequestBuilder.() -> Unit) {
        apply { payload = PrintImageRequestBuilder().apply { block() }.build() }
    }

    fun payload(block: SendRequestDirectiveBuilder.() -> Payload) {
        apply { payload = block() }
    }

    fun build(): SendRequestDirective = SendRequestDirective(name, payload, token)
}
