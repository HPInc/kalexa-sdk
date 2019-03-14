/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.geolocation


data class Geolocation(
        val timestamp: String? = null,
        val coordinate: Coordinate? = null,
        val altitude: Altitude? = null,
        val heading: Heading? = null,
        val speed: Speed? = null,
        val locationServices: LocationServices? = null)