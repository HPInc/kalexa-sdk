//package com.hp.kalexa.core
//
//import com.google.common.reflect.ClassPath
//import com.hp.kalexa.core.annotation.Intent
//import com.hp.kalexa.core.intent.IntentHandler
//import com.hp.kalexa.core.util.Util
//import kotlin.reflect.KClass
//import kotlin.reflect.full.declaredFunctions
//import kotlin.reflect.full.findAnnotation
//import kotlin.reflect.full.superclasses
//
//
//fun main(args: Array<String>) {
//    val classes = ClassPath.from(Thread.currentThread().contextClassLoader).getTopLevelClasses("com.hp.kalexa.core.model")
//    val klazzes = classes.map { it.load().kotlin }
//            .filter { it.superclasses.find { it.simpleName == IntentHandler::class.java.simpleName } != null }
//            .associate { it.simpleName!! to it as KClass<out IntentHandler> }
//
//
//    val intentsAnnotationPairList = Util.findAnnotatedMethod(klazzes, Intent::class, "onIntentRequest")
//            .map { entry ->
//                val onIntentRequestMethod = entry.value.declaredFunctions.find { it.name == "onIntentRequest" }!!
//                val intents = onIntentRequestMethod.findAnnotation<Intent>()!!
//                intents.mapTo.map {
//                    it to entry.value
//                }
//            }.flatten()
//    println(klazzes + intentsAnnotationPairList)
//}