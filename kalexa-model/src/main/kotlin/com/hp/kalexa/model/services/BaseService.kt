/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services

interface BaseService {

    fun getRequestHeaders(token: String): Map<String, String> {
        return mapOf(
            "Authorization" to "Bearer $token",
            "Content-Type" to "application/json"
        )
    }

    companion object {
        const val API_ENDPOINT = "https://api.amazonalexa.com"
    }
}
