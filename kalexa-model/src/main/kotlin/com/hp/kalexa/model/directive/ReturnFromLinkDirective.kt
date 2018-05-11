package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hp.kalexa.model.ConnectionsStatus
import com.hp.kalexa.model.json.PayloadSerializer
import com.hp.kalexa.model.payload.Payload

@JsonTypeName("Links.ReturnFromLink")
class ReturnFromLinkDirective(
        val status: Status,
        @JsonSerialize(using = PayloadSerializer::class)
        val payload: Payload<*>?) : Directive() {

    enum class Status {
        SUCCESS, FAILURE
    }
}