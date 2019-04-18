/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gadgetcontroller

data class LightAnimation(
    var repeat: Int? = null,
    var targetLights: List<String>? = null,
    var sequence: List<AnimationStep>? = null
)
