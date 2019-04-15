/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.interceptor.RequestInterceptor
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.request.AlexaRequest
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.superclasses

class ConcreteInterceptorHandler(interceptorInstances: List<RequestInterceptor> = emptyList()) : InterceptorHandler {
    private val logger = LogManager.getLogger(ConcreteSpeechHandler::class.java)

    private val interceptors = loadInterceptorClasses().map { it.createInstance() } + interceptorInstances

    override fun process(requestEnvelope: AlexaRequest<*>) {
        for (interceptor in interceptors) {
            interceptor.intercept(requestEnvelope)
        }
    }

    @Suppress("unchecked_cast")
    private fun loadInterceptorClasses(): List<KClass<out RequestInterceptor>> {
        return Util.loadIntentClassesFromPackage()
            .filter { clazz ->
                clazz.superclasses.find { superclazz ->
                    superclazz.simpleName == RequestInterceptor::class.java.simpleName
                } != null
            }
            .cast()
    }
}
