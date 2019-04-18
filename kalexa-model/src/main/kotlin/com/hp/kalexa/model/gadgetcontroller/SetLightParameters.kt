/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gadgetcontroller

data class SetLightParameters(
    var triggerEvent: TriggerEventType? = null,
    var triggerEventTimeMs: Int? = null,
    var animations: List<LightAnimation>? = null
)
