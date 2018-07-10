		package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.payload.Payload

@JsonTypeName("Connections.SendResponse")
class SendResponseDirective(
        val status: Status,
        val payload: Map<String, Any>?) : Directive() {

    enum class Status {
        SUCCESS, FAILURE
    }
}