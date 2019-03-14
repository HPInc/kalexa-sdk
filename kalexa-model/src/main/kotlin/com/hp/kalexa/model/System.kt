/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

data class System(
        val application: Application,
        val user: User,
        val device: Device? = null,
        val apiEndpoint: String = "",
        val apiAccessToken: String = "")

