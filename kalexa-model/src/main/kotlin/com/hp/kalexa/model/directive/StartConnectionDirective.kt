/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.connections.UriType
import com.hp.kalexa.model.connections.Input

@JsonTypeName("Connections.StartConnection")
class StartConnectionDirective(
    val uri: UriType,
    val input: Input,
    val token: String
) : Directive()
