/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Session


class AlexaRequest<out T : Request>(
        val version: String,
        val session: Session?,
        val context: Context,
        val request: T)