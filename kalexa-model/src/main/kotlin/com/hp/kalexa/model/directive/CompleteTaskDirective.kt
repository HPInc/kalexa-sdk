/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.Cause
import com.hp.kalexa.model.connections.Cause

@JsonTypeName("Connections.SendResponse")
class SendResponseDirective(
    val status: Cause,
    val payload: Map<String, Any>?
) : Directive()
