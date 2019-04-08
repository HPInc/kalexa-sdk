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

    fun getIntentPackage(): String? = System.getenv("INTENT_PACKAGE")

    fun <T : Annotation> findAnnotatedClasses(
        intentClasses: Set<KClass<out IntentHandler>>,
        annotation: KClass<T>
    ): Set<KClass<out IntentHandler>> {
        return intentClasses.filter {
            it.findAnnotation(annotation) != null
        }.toSet()
    }

    fun loadIntentClassesFromPackage(): Set<KClass<out Any>> {
        val intentPackage = getIntentPackage() ?: ""
        return if (intentPackage.isNotEmpty()) {
            ClassPath.from(Thread.currentThread().contextClassLoader)
                .getTopLevelClasses(intentPackage)
                .map { it.load().kotlin }
                .toSet()
        } else {
            emptySet()
        }
    }
}
