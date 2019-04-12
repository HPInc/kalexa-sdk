package com.hp.kalexa.core.handler

import com.hp.kalexa.model.request.AlexaRequest

interface RequestInterceptor {

    fun intercept(alexaRequest: AlexaRequest<*>)
}
