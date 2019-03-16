/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.interfaces.display.Hint

@JsonTypeName("Hint")
data class HintDirective(var hint: Hint? = null) : Directive()
