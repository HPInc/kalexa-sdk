/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

data class Device(
    val deviceId: String,
    val supportedInterfaces: SupportedInterfaces? = null
)