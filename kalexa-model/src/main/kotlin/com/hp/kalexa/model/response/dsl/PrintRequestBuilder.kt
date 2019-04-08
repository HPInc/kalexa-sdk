package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.payload.print.PrintBuilder
import com.hp.kalexa.model.payload.print.PrintImageRequest
import com.hp.kalexa.model.payload.print.PrintPDFRequest
import com.hp.kalexa.model.payload.print.PrintWebPageRequest
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class PrintPDFRequestBuilder : PrintBuilder<PrintPDFRequest>() {

    override fun build() = PrintPDFRequest(version, context, title, description, url)

    companion object {
        @JvmStatic
        fun builder() = PrintPDFRequestBuilder()
    }
}

@AlexaResponseDsl
class PrintWebPageRequestBuilder : PrintBuilder<PrintWebPageRequest>() {
    override fun build() = PrintWebPageRequest(version, context, title, description, url)

    companion object {
        @JvmStatic
        fun builder() = PrintWebPageRequestBuilder()
    }
}

@AlexaResponseDsl
class PrintImageRequestBuilder : PrintBuilder<PrintImageRequest>() {

    private lateinit var imageType: PrintImageRequest.ImageType
    fun imageType(block: () -> PrintImageRequest.ImageType) = apply {
        imageType = block()
    }

    override fun build() = PrintImageRequest(version, context, title, description, imageType, url)

    companion object {
        @JvmStatic
        fun builder() = PrintImageRequestBuilder()
    }
}
