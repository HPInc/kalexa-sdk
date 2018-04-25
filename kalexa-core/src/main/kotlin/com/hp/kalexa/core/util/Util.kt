package com.hp.kalexa.core.util

import com.hp.kalexa.core.extension.findAnnotation
import com.hp.kalexa.core.intent.IntentExecutor
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions

object Util {
    fun getSkillName() = System.getenv("SKILL_NAME") ?: "This Skill"

    fun getIntentPackage() = System.getenv("INTENT_PACKAGE")
            ?: throw IllegalArgumentException("You must define INTENT_PACKAGE environment variable")

    @Suppress("unchecked_cast")
    fun <T : Annotation> findAnnotatedMethod(intentClasses: Map<String, KClass<out IntentExecutor>>, annotation: KClass<T>, methodName: String): Map<String, KClass<out IntentExecutor>> {
        return intentClasses.filter {
            it.value.declaredFunctions.find {
                it.findAnnotation(annotation) != null && it.name == methodName
            } != null
        }
    }
}