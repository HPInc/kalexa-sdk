package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hp.kalexa.model.json.PayloadSerializer
import com.hp.kalexa.model.payload.NameType
import com.hp.kalexa.model.payload.Payload

@JsonTypeName("Connections.SendRequest")
class SendRequestDirective(
        val name: NameType,
        @JsonSerialize(using = PayloadSerializer::class)
        val payload: Payload<*>,
        val token: String) : Directive()