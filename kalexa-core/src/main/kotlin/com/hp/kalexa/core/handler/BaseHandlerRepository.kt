/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.core.util.Util
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

class BaseHandlerRepository(intentHandlers: List<BaseHandler> = emptyList()) {
    val handlerClasses: Set<KClass<out BaseHandler>> = getClassesFrom(intentHandlers) + loadBaseHandlerClasses()
    val handlerInstances: MutableMap<String, BaseHandler> = toMap(intentHandlers)

    private fun toMap(intentHandlers: List<BaseHandler>) =
        intentHandlers.map { it::class.simpleName!! to it }.toMap().toMutableMap()

    private fun getClassesFrom(intentHandlers: List<BaseHandler>) =
        intentHandlers.map { it::class }.toSet()

    /**
     * Loads all BaseHandler classes from the SCAN_PACKAGE environment variable.
     */
    @Suppress("unchecked_cast")
    fun loadBaseHandlerClasses(): Set<KClass<out BaseHandler>> {
        return Util.loadClassesFromPackage()
            .filter { clazz ->
                clazz.superclasses.find { superclazz ->
                    superclazz.simpleName == BaseHandler::class.java.simpleName || superclazz.superclasses.find {
                        it.simpleName == BaseHandler::class.java.simpleName
                    } != null
                } != null
            }
            .toSet()
            .cast()
    }
}
