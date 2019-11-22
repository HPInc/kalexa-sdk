/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.interceptor

import com.hp.kalexa.model.response.AlexaResponse

class InterceptorException(
    private val msg: String,
    val responseCallback: () -> AlexaResponse = { AlexaResponse.emptyResponse() }
) : Exception(msg)
