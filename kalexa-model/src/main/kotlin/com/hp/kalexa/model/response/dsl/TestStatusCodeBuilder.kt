/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.connections.Context
import com.hp.kalexa.model.connections.test.TestStatusCode
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class TestStatusCodeBuilder {
    var version: String = ""
    var context: Context? = null
    lateinit var code: String

    fun build() = TestStatusCode(code, version, context)
}
