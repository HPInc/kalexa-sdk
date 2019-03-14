/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.ConnectionsStatus

@JsonTypeName("Connections.SendResponse")
class SendResponseDirective(
        val status: ConnectionsStatus,
        val payload: Map<String, Any>?) : Directive()