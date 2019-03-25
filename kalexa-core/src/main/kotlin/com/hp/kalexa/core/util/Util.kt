/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.util

import com.google.common.reflect.ClassPath
import com.hp.kalexa.core.extension.findAnnotation
import com.hp.kalexa.core.intent.IntentHandler
import kotlin.reflect.KClass

object Util {
    fun getSkillName() = System.getenv("SKILL_NAME") ?: "This Skill"

    fun getApplicationID(): String? = System.getenv("APPLICATION_ID")

    fun getIntentPackage() = System.getenv("INTENT_PACKAGE")
        ?: throw IllegalArgumentException("You must define INTENT_PACKAGE environment variable")

    fun <T : Annotation> findAnnotatedClasses(
        intentClasses: List<KClass<out IntentHandler>>,
        annotation: KClass<T>
    ): List<KClass<out IntentHandler>> {
        return intentClasses.filter {
            it.findAnnotation(annotation) != null
        }
    }

    fun loadIntentClassesFromPackage(): List<KClass<out Any>> {
        return ClassPath.from(Thread.currentThread().contextClassLoader)
            .getTopLevelClasses(getIntentPackage())
            .map { it.load().kotlin }
    }
}
