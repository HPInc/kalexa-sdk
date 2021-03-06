/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

data class User(
    val userId: String? = null,
    val accessToken: String? = null,
    val permissions: Permissions? = null
)
