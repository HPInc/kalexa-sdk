package com.hp.kalexa.model.services.deviceAddress

import com.hp.kalexa.model.services.ServiceException


interface DeviceAddressService {
    @Throws(ServiceException::class)
    fun getCountryAndPostalCode(deviceId: String, token: String): ShortAddress

    @Throws(ServiceException::class)
    fun getFullAddress(deviceId: String, token: String): Address
}