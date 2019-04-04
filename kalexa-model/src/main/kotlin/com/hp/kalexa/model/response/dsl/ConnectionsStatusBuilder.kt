package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.ConnectionsStatus
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class ConnectionsStatusBuilder {
    var code: String = ""
    var message: String = ""
    fun build() = ConnectionsStatus(code, message)
}
