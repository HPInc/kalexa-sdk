/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.util

import com.google.common.reflect.ClassPath
import com.hp.kalexa.core.extension.findAnnotation
import com.hp.kalexa.core.intent.BaseHandler
import kotlin.reflect.KClass

object Util {
    fun getSkillName() = System.getenv("SKILL_NAME") ?: "This Skill"

    fun getApplicationID(): String? = System.getenv("APPLICATION_ID")

    fun getScanPackage(): String? = System.getenv("SCAN_PACKAGE")

    fun isApplicationIdVerificationEnabled(): Boolean =
        System.getenv("APPLICATION_ID_VERIFICATION")?.toBoolean() ?: true

    fun loadClassesFromPackage() = classesFromPackage

    fun <T : Annotation> findAnnotatedClasses(
        intentClasses: Set<KClass<out BaseHandler>>,
        annotation: KClass<T>
    ): Set<KClass<out BaseHandler>> {
        return intentClasses.filter {
            it.findAnnotation(annotation) != null
        }.toSet()
    }

    private val classesFromPackage by lazy {
        val intentPackage = getScanPackage() ?: ""
        if (intentPackage.isNotEmpty()) {
            ClassPath.from(Thread.currentThread().contextClassLoader)
                .getTopLevelClassesRecursive(intentPackage)
                .map { it.load().kotlin }
                .toSet()
        } else {
            emptySet()
        }
    }
}
