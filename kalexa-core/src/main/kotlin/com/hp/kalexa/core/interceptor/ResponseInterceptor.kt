/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.interceptor

import com.hp.kalexa.model.response.AlexaResponse

interface ResponseInterceptor {

    @Throws(InterceptorException::class)
    fun intercept(alexaResponse: AlexaResponse): AlexaResponse
}
