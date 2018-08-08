package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.ConnectionsStatus

@JsonTypeName("Connections.SendResponse")
class SendResponseDirective(
        val status: ConnectionsStatus,
        val payload: Map<String, Any>?) : Directive()