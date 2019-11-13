/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.connections.ConnectionStatus
import com.hp.kalexa.model.directive.CompleteTaskDirective
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class CompleteTaskDirectiveBuilder {
    lateinit var status: ConnectionStatus

    fun status(block: ConnectionStatusBuilder.() -> Unit) {
        status = ConnectionStatusBuilder().apply { block() }.build()
    }

    fun build(): CompleteTaskDirective = CompleteTaskDirective(status)
}
