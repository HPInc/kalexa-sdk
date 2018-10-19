package com.hp.kalexa.model.payload

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.hp.kalexa.model.payload.print.PrintImageRequest
import com.hp.kalexa.model.payload.print.PrintPDFRequest
import com.hp.kalexa.model.payload.print.PrintWebPageRequest

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
@JsonSubTypes(
        JsonSubTypes.Type(value = PrintWebPageRequest::class, name = "PrintWebPageRequest"),
        JsonSubTypes.Type(value = PrintPDFRequest::class, name = "PrintPDFRequest"),
        JsonSubTypes.Type(value = PrintImageRequest::class, name = "PrintImageRequest"))
abstract class Payload(
        @field:JsonProperty("@version")
        val version: String,
        @field:JsonProperty("context")
        val context: Context? = null) {

    companion object {
        inline fun <reified T : Payload> newRequest(block: T.() -> Unit): Payload {
            return T::class.java.newInstance().apply(block)
        }
    }
}