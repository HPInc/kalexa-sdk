package com.hp.kalexa.core.annotation

@Target(AnnotationTarget.FUNCTION)
annotation class Launcher

@Target(AnnotationTarget.FUNCTION)
annotation class Helper

@Target(AnnotationTarget.FUNCTION)
annotation class Intents(vararg val intentNames: String)