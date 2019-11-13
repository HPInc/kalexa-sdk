/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.connections.ConnectionStatus

@JsonTypeName("Tasks.CompleteTask")
class CompleteTaskDirective(
    val status: ConnectionStatus
) : Directive()
