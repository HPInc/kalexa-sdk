package com.hp.kalexa.model.services.ups

import com.hp.kalexa.model.services.ServiceException


interface UpsService {
    @Throws(ServiceException::class)
    fun getProfileEmail(token: String): String

    @Throws(ServiceException::class)
    fun getProfileGivenName(token: String): String

    @Throws(ServiceException::class)
    fun getProfileMobileNumber(token: String): PhoneNumber

    @Throws(ServiceException::class)
    fun getProfileName(token: String): String

    @Throws(ServiceException::class)
    fun getSystemDistanceUnits(deviceId: String, token: String): DistanceUnit

    @Throws(ServiceException::class)
    fun getSystemTemperatureUnit(deviceId: String, token: String): TemperatureUnit

    @Throws(ServiceException::class)
    fun getSystemTimeZone(deviceId: String, token: String): String
}