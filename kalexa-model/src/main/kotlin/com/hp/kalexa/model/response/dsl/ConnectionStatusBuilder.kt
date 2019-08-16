/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.connections.ConnectionStatus

class ConnectionStatusBuilder {
    var code: String = ""
    var message: String = ""

    fun build() = ConnectionStatus(code, message)
}
