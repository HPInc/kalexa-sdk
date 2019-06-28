/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.ups

import com.hp.kalexa.model.services.ServiceException

interface UpsService {
    @Throws(ServiceException::class)
    fun getProfileEmail(): String

    @Throws(ServiceException::class)
    fun getProfileGivenName(): String

    @Throws(ServiceException::class)
    fun getProfileMobileNumber(): PhoneNumber

    @Throws(ServiceException::class)
    fun getProfileName(): String

    @Throws(ServiceException::class)
    fun getSystemDistanceUnits(deviceId: String): DistanceUnit

    @Throws(ServiceException::class)
    fun getSystemTemperatureUnit(deviceId: String): TemperatureUnit

    @Throws(ServiceException::class)
    fun getSystemTimeZone(deviceId: String): String
}
