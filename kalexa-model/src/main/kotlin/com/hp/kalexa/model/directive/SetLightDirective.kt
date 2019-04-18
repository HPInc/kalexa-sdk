/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.gadgetcontroller.SetLightParameters

@JsonTypeName("GadgetController.SetLight")
data class SetLightDirective(
    val version: Int? = null,
    var targetGadgets: List<String>? = null,
    var parameters: SetLightParameters? = null
) : Directive()
