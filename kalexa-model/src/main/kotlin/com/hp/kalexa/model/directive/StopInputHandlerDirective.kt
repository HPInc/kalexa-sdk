/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("GameEngine.StopInputHandler")
data class StopInputHandlerDirective(
    var originatingRequestId: String? = null
) : Directive()
