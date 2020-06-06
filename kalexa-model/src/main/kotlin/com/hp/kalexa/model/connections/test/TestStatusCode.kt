/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.connections.test

import com.hp.kalexa.model.connections.Context
import com.hp.kalexa.model.connections.Input

class TestStatusCode(
    val code: String,
    version: String = "1",
    context: Context? = null
) : Input(version, context)
