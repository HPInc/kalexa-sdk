package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hp.kalexa.model.Directive
import com.hp.kalexa.model.LinkStatus
import com.hp.kalexa.model.json.PayloadSerializer
import com.hp.kalexa.model.payload.Payload

@JsonTypeName("Links.ReturnFromLink")
class ReturnFromLinkDirective(
        val status: String,
        val linkStatus: LinkStatus,
        @JsonSerialize(using = PayloadSerializer::class)
        val payload: Payload<*>?) : Directive()