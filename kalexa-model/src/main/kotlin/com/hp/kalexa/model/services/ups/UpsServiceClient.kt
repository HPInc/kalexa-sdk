/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.ups

import com.hp.kalexa.model.services.ApiConfiguration
import com.hp.kalexa.model.services.BaseService
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException

class UpsServiceClient(private val apiConfig: ApiConfiguration) : UpsService,
    BaseService(apiConfig.apiClient) {

    override fun getProfileEmail(): String {
        val uri = "${apiConfig.apiEndpoint}/v2/accounts/~current/settings/Profile.email"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve profile email", e)
        }
    }

    override fun getProfileGivenName(): String {
        val uri = "${apiConfig.apiEndpoint}/v2/accounts/~current/settings/Profile.givenName"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve profile given name", e)
        }
    }

    override fun getProfileMobileNumber(): PhoneNumber {
        val uri = "${apiConfig.apiEndpoint}/v2/accounts/~current/settings/Profile.mobileNumber"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve profile mobile number", e)
        }
    }

    override fun getProfileName(): String {
        val uri = "${apiConfig.apiEndpoint}/v2/accounts/~current/settings/Profile.name"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve profile name", e)
        }
    }

    override fun getSystemDistanceUnits(deviceId: String): DistanceUnit {
        val uri = "${apiConfig.apiEndpoint}/v2/devices/{deviceId}/settings/System.distanceUnits"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve distance units", e)
        }
    }

    override fun getSystemTemperatureUnit(deviceId: String): TemperatureUnit {
        val uri = "${apiConfig.apiEndpoint}/v2/devices/{deviceId}/settings/System.temperatureUnit"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve temperature unit", e)
        }
    }

    override fun getSystemTimeZone(deviceId: String): String {
        val uri = "${apiConfig.apiEndpoint}/v2/devices/{deviceId}/settings/System.timeZone"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve system timezone", e)
        }
    }
}
