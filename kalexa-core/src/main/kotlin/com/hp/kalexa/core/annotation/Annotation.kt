package com.hp.kalexa.core.annotation

@Target(AnnotationTarget.CLASS)
annotation class Launcher

@Target(AnnotationTarget.CLASS)
annotation class RecoverIntentContext

@Target(AnnotationTarget.CLASS)
annotation class Fallback

@Target(AnnotationTarget.CLASS)
annotation class Helper

@Target(AnnotationTarget.CLASS)
annotation class Intents(vararg val intentNames: String)

@Target(AnnotationTarget.CLASS)
annotation class Fullfiller