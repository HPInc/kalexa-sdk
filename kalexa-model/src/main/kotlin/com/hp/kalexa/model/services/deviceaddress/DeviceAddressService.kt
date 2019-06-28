/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.deviceaddress

import com.hp.kalexa.model.services.ServiceException

interface DeviceAddressService {
    @Throws(ServiceException::class)
    fun getCountryAndPostalCode(deviceId: String): ShortAddress

    @Throws(ServiceException::class)
    fun getFullAddress(deviceId: String): Address
}
