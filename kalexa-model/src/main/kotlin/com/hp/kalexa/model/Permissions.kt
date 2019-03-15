/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

data class Permissions(
    val consentToken: String? = null,
    val scopes: Map<String, Scope>? = null
)
