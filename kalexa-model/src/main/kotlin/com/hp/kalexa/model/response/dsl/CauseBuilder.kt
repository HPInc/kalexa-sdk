/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.Cause
import com.hp.kalexa.model.connections.Cause
import com.hp.kalexa.model.connections.ConnectionStatus
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class CauseBuilder {
    var code: String = ""
    var message: String = ""
    var status: ConnectionStatus
    fun build() = Cause(code, message)
}
