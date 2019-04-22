/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.ups

import com.hp.kalexa.model.services.ApiClient
import com.hp.kalexa.model.services.BaseService.Companion.API_ENDPOINT
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException

class UpsServiceClient(private val client: ApiClient = ApiClient()) : UpsService {

    override fun getProfileEmail(token: String): String {
        val uri = "$API_ENDPOINT/v2/accounts/~current/settings/Profile.email"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve profile email", e)
        }
    }

    override fun getProfileGivenName(token: String): String {
        val uri = "$API_ENDPOINT/v2/accounts/~current/settings/Profile.givenName"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve profile given name", e)
        }
    }

    override fun getProfileMobileNumber(token: String): PhoneNumber {
        val uri = "$API_ENDPOINT/v2/accounts/~current/settings/Profile.mobileNumber"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve profile mobile number", e)
        }
    }

    override fun getProfileName(token: String): String {
        val uri = "$API_ENDPOINT/v2/accounts/~current/settings/Profile.name"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve profile name", e)
        }
    }

    override fun getSystemDistanceUnits(deviceId: String, token: String): DistanceUnit {
        val uri = "$API_ENDPOINT/v2/devices/{deviceId}/settings/System.distanceUnits"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve distance units", e)
        }
    }

    override fun getSystemTemperatureUnit(deviceId: String, token: String): TemperatureUnit {
        val uri = "$API_ENDPOINT/v2/devices/{deviceId}/settings/System.temperatureUnit"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve temperature unit", e)
        }
    }

    override fun getSystemTimeZone(deviceId: String, token: String): String {
        val uri = "$API_ENDPOINT/v2/devices/{deviceId}/settings/System.timeZone"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve system timezone", e)
        }
    }
}
