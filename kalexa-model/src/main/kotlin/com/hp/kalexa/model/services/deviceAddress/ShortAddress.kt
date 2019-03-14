/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.deviceAddress

data class ShortAddress(
        val countryCode: String? = null,
        val postalCode: String? = null)