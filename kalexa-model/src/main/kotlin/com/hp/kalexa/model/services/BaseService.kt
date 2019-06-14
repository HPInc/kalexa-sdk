/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services

import java.io.IOException

abstract class BaseService(private val apiClient: ApiClient) {

    fun getRequestHeaders(token: String): Map<String, String> {
        return mapOf(
            "Authorization" to "Bearer $token",
            "Content-Type" to "application/json"
        )
    }

    @Throws(IOException::class)
    fun post(uri: String, headers: Map<String, String>, body: String): ApiClientResponse {
        return apiClient.dispatch(uri, headers, body, "POST")
    }

    @Throws(IOException::class)
    fun put(uri: String, headers: Map<String, String>, body: String): ApiClientResponse {
        return apiClient.dispatch(uri, headers, body, "PUT")
    }

    @Throws(IOException::class)
    fun get(uri: String, headers: Map<String, String>): ApiClientResponse {
        return apiClient.dispatch(uri, headers, null, "GET")
    }

    @Throws(IOException::class)
    fun delete(uri: String, headers: Map<String, String>): ApiClientResponse {
        return apiClient.dispatch(uri, headers, null, "DELETE")
    }
}
