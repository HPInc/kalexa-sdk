package com.hp.kalexa.core.handler

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.Request

interface RequestInterceptor<T : Request> {
    val obj: T?
        get() = null

    fun intercept(alexaRequest: AlexaRequest<T>)
}