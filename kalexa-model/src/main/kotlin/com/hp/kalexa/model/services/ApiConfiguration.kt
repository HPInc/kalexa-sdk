/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services

class ApiConfiguration(val apiAccessToken: String, val apiEndpoint: String) {
    val apiClient: ApiClient = ApiClient()
}
