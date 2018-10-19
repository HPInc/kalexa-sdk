package com.hp.kalexa.model.payload.print

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.payload.Context
import com.hp.kalexa.model.payload.Payload

@JsonTypeName("PrintWebPageRequest")
class PrintPDFRequest(
        version: String = "1",
        context: Context? = null,
        var title: String,
        var description: String? = null,
        var url: String) : Payload(version, context)
