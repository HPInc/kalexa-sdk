/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.deviceaddress

import com.hp.kalexa.model.services.ApiClient
import com.hp.kalexa.model.services.BaseService.Companion.API_ENDPOINT
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException

class DeviceAddressServiceClient(private val client: ApiClient = ApiClient()) : DeviceAddressService {

    override fun getCountryAndPostalCode(deviceId: String, token: String): ShortAddress {
        val uri = "$API_ENDPOINT/v1/devices/$deviceId/settings/address/countryAndPostalCode"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve short address", e)
        }
    }

    override fun getFullAddress(deviceId: String, token: String): Address {
        val uri = "$API_ENDPOINT/v1/devices/$deviceId/settings/address"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve full address", e)
        }
    }
}
