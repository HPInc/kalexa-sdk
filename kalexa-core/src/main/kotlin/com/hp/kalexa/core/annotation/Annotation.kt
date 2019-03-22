/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.annotation

@Target(AnnotationTarget.CLASS)
annotation class LaunchIntent

@Target(AnnotationTarget.CLASS)
annotation class RecoverIntentContext

@Target(AnnotationTarget.CLASS)
annotation class FallbackIntent

@Target(AnnotationTarget.CLASS)
annotation class HelpIntent

@Target(AnnotationTarget.CLASS)
annotation class Intent(val mapsTo: Array<String> = [])

@Target(AnnotationTarget.CLASS)
annotation class Provider

@Target(AnnotationTarget.CLASS)
annotation class Requester

@Target(AnnotationTarget.CLASS)
annotation class CanFulfillIntent

@Target(AnnotationTarget.CLASS)
annotation class ListEvents
