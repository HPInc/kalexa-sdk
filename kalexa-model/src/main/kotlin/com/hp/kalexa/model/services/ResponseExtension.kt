/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services

import com.hp.kalexa.model.json.JacksonSerializer

inline fun <reified T> ApiClientResponse.toTypedObject(): T {
    return if (responseCode in 200..299) {
        if (responseBody.isNotEmpty()) {
            JacksonSerializer.OBJECT_MAPPER.readValue(responseBody, T::class.java)
        } else {
            throw ServiceException("Empty Response body.")
        }
    } else {
        throw ServiceException(responseBody)
    }
}