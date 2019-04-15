/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.annotation

@Target(AnnotationTarget.FUNCTION)
annotation class Intent(val mapsTo: Array<String> = [])

@Target(AnnotationTarget.FUNCTION)
annotation class CanFulfillIntent(val intents: Array<String> = [])
