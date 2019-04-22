/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.deviceaddress

import com.hp.kalexa.model.services.BaseService
import com.hp.kalexa.model.services.ServiceException

interface DeviceAddressService : BaseService {
    @Throws(ServiceException::class)
    fun getCountryAndPostalCode(deviceId: String, token: String): ShortAddress

    @Throws(ServiceException::class)
    fun getFullAddress(deviceId: String, token: String): Address
}
