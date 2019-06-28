/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.deviceaddress

import com.hp.kalexa.model.services.ApiConfiguration
import com.hp.kalexa.model.services.BaseService
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException

class DeviceAddressServiceClient(private val apiConfig: ApiConfiguration) : DeviceAddressService,
    BaseService(apiConfig.apiClient) {

    override fun getCountryAndPostalCode(deviceId: String): ShortAddress {
        val uri = "${apiConfig.apiEndpoint}/v1/devices/$deviceId/settings/address/countryAndPostalCode"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve short address", e)
        }
    }

    override fun getFullAddress(deviceId: String): Address {
        val uri = "${apiConfig.apiEndpoint}/v1/devices/$deviceId/settings/address"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve full address", e)
        }
    }
}
