/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.ConnectionsStatus
import com.hp.kalexa.model.directive.SendResponseDirective
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class SendResponseDirectiveBuilder {
    lateinit var status: ConnectionsStatus
    lateinit var payload: Map<String, Any>

    fun status(block: () -> ConnectionsStatus) {
        apply { status = block() }
    }

    fun payload(block: SendResponseDirectiveBuilder.() -> Map<String, Any>) {
        apply { payload = block() }
    }

    fun build(): SendResponseDirective = SendResponseDirective(status, payload)
}
