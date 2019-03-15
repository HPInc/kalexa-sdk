/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.geolocation

data class Speed(
    val speedInMetersPerSecond: Double? = null,
    val accuracyInMetersPerSecond: Double? = null
)