/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.connections.Cause
import com.hp.kalexa.model.connections.ConnectionStatus
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class CauseBuilder {
    lateinit var status: ConnectionStatus
    var token = ""
    var result: Map<String, Any> = emptyMap()
    lateinit var type: String

    fun status(block: ConnectionStatusBuilder.() -> Unit) {
        status = ConnectionStatusBuilder().apply { block() }.build()
    }

    fun build() = Cause(result, status, token, type)
}
