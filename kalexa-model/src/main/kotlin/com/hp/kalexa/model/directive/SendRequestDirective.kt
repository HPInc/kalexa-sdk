package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.payload.NameType
import com.hp.kalexa.model.payload.Payload

@JsonTypeName("Connections.SendRequest")
class SendRequestDirective(
        val name: NameType,
        val payload: Payload,
        val token: String) : Directive()