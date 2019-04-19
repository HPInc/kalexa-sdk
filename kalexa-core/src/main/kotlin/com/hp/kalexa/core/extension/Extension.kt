/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.extension

import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.model.json.JacksonSerializer
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.jvmName

@Suppress("UNCHECKED_CAST")
fun <T : Annotation> KAnnotatedElement.findAnnotation(clazz: KClass<T>): T? = annotations.firstOrNull {
    it.annotationClass::jvmName == clazz::jvmName
} as T?

inline fun <reified T> Any.cast(): T = this as T

inline fun <reified T> Any.toValueType(): T {
    return JacksonSerializer.convert(this, T::class.java)
}

fun KClass<out BaseHandler>.hasSuperClassOf(superclazz: KClass<out BaseHandler>): Boolean {
    return superclasses.find { clazz ->
        clazz == superclazz
    } != null
}