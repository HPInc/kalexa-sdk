/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.interceptor

import com.hp.kalexa.core.interceptor.RequestInterceptor
import com.hp.kalexa.core.interceptor.ResponseInterceptor
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.response.AlexaResponse

interface InterceptorHandler {

    fun process(requestEnvelope: AlexaRequest<*>)

    fun process(alexaResponse: AlexaResponse): AlexaResponse

    companion object {
        fun newInstance(
            requestInterceptors: List<RequestInterceptor> = emptyList(),
            responseInterceptors: List<ResponseInterceptor> = emptyList()
        ): InterceptorHandler =
            DefaultInterceptorHandler(requestInterceptors, responseInterceptors)
    }
}
