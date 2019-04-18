/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gadgetcontroller

data class AnimationStep(
    var durationMs: Int? = null,
    var color: String? = null,
    var blend: Boolean? = null
)
