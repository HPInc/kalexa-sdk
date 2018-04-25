package com.hp.kalexa.core.extension

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName


@Suppress("UNCHECKED_CAST")
fun <T : Annotation> KAnnotatedElement.findAnnotation(clazz: KClass<T>): T? = annotations.firstOrNull {
    it.annotationClass::jvmName == clazz::jvmName
} as T?

inline fun <reified T> Any.cast(): T = this as T
