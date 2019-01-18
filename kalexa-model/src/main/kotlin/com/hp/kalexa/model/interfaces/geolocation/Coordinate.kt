package com.hp.kalexa.model.interfaces.geolocation

data class Coordinate(
        val latitudeInDegrees: Double? = null,
        val longitudeInDegrees: Double? = null,
        val accuracyInMeters: Double? = null)