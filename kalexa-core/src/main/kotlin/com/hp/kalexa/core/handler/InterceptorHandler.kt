/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.interceptor.RequestInterceptor
import com.hp.kalexa.model.request.AlexaRequest

interface InterceptorHandler {

    fun process(requestEnvelope: AlexaRequest<*>)

    companion object {
        fun newInstance(interceptorInstances: List<RequestInterceptor> = emptyList()): InterceptorHandler =
            ConcreteInterceptorHandler(interceptorInstances)
    }
}
