/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.interceptor

import com.hp.kalexa.model.request.AlexaRequest

interface RequestInterceptor {

    fun intercept(alexaRequest: AlexaRequest<*>)
}
