package com.hp.kalexa.core.interceptor

import com.hp.kalexa.model.request.AlexaRequest

interface RequestInterceptor {

    fun intercept(alexaRequest: AlexaRequest<*>)
}
