package com.hp.kalexa.core.util

import com.google.common.reflect.ClassPath
import com.hp.kalexa.core.extension.findAnnotation
import com.hp.kalexa.core.intent.IntentExecutor
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions

object Util {
    fun getSkillName() = System.getenv("SKILL_NAME") ?: "This Skill"

    fun getIntentPackage() = System.getenv("INTENT_PACKAGE")
            ?: throw IllegalArgumentException("You must define INTENT_PACKAGE environment variable")

    @Suppress("unchecked_cast")
    fun <T : Annotation> findAnnotatedMethod(intentClasses: Map<String, KClass<out IntentExecutor>>, annotation: KClass<T>): List<KFunction<*>> {
        val functions = intentClasses.map { entry ->
            entry.value.declaredFunctions.find {
                it.findAnnotation(annotation) != null
            }
        }
        return if (functions.isNotEmpty()) functions as List<KFunction<*>> else emptyList()
    }

    @Suppress("unchecked_cast")
    fun <T : Annotation> findAnnotatedMethod(intentClasses: Map<String, KClass<out IntentExecutor>>, annotation: KClass<T>, methodName: String): Map<String, KClass<out IntentExecutor>> {
        return intentClasses.filter {
            it.value.declaredFunctions.find {
                it.findAnnotation(annotation) != null && it.name == methodName
            } != null
        }
    }

    fun <T : Annotation> findAnnotatedClasses(intentClasses: Map<String, KClass<out IntentExecutor>>, annotation: KClass<T>): Map<String, KClass<out IntentExecutor>> {
        return intentClasses.filter {
            it.value.findAnnotation(annotation) != null
        }
    }

    fun <T : Annotation> getMethodAnnotation(clazz: KClass<out Any>, methodName: String, annotation: KClass<T>): Annotation? {
        val kFunction = clazz.declaredFunctions.find { it.name == methodName }
        return kFunction?.findAnnotation(annotation)
    }

    fun getAnnotation(clazz: KClass<out Any>, methodName: String): KFunction<*>? {
        return clazz.declaredFunctions.find { it.name == methodName }
    }

    fun loadIntentClassesFromPackage(): List<KClass<out Any>> {
        return ClassPath.from(Thread.currentThread().contextClassLoader)
                .getTopLevelClasses(getIntentPackage())
                .map { it.load().kotlin }
    }
}