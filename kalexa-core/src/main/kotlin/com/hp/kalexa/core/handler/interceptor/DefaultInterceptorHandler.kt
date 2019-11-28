/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.interceptor

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.interceptor.RequestInterceptor
import com.hp.kalexa.core.interceptor.ResponseInterceptor
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.response.AlexaResponse
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.superclasses

class DefaultInterceptorHandler(
    interceptorInstances: List<RequestInterceptor> = emptyList(),
    responseInterceptorsInstances: List<ResponseInterceptor> = emptyList()
) :
    InterceptorHandler {
    private val logger = LogManager.getLogger(DefaultInterceptorHandler::class.java)

    private val interceptors = loadInterceptorClasses().map { it.createInstance() } + interceptorInstances
    private val responseInterceptors =
        loadResponseInterceptorClasses().map { it.createInstance() } + responseInterceptorsInstances

    override fun process(requestEnvelope: AlexaRequest<*>) {
        for (interceptor in interceptors) {
            interceptor.intercept(requestEnvelope)
        }
    }

    override fun process(alexaResponse: AlexaResponse): AlexaResponse {
        var response = alexaResponse
        for (interceptor in responseInterceptors) {
            response = interceptor.intercept(alexaResponse)
        }
        return response
    }

    @Suppress("unchecked_cast")
    private fun loadInterceptorClasses(): List<KClass<out RequestInterceptor>> {
        return Util.loadClassesFromPackage()
            .filter { clazz ->
                clazz.superclasses.find { superclazz ->
                    superclazz.simpleName == RequestInterceptor::class.java.simpleName
                } != null
            }
            .cast()
    }

    @Suppress("unchecked_cast")
    private fun loadResponseInterceptorClasses(): List<KClass<out ResponseInterceptor>> {
        return Util.loadClassesFromPackage()
            .filter { clazz ->
                clazz.superclasses.find { superclazz ->
                    superclazz.simpleName == ResponseInterceptor::class.java.simpleName
                } != null
            }
            .cast()
    }
}
