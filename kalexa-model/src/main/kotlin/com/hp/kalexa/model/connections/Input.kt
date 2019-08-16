/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

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
    property = "@type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = PrintWebPageRequest::class, name = "PrintWebPageRequest"),
    JsonSubTypes.Type(value = PrintPDFRequest::class, name = "PrintPDFRequest"),
    JsonSubTypes.Type(value = PrintImageRequest::class, name = "PrintImageRequest")
)
abstract class Input(
    @field:JsonProperty("@version")
    val version: String
) {

    companion object {
        inline fun <reified T : Input> newRequest(block: T.() -> Unit): Input {
            return T::class.java.newInstance().apply(block)
        }
    }
}
