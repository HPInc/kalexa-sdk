/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.geolocation

data class Heading(
    val directionInDegrees: Double? = null,
    val accuracyInDegrees: Double? = null
)
