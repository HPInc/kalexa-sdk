package com.hp.kalexa.core.handler

import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.core.interceptor.RequestInterceptor

data class SkillConfig(
    val intentHandlers: List<BaseHandler> = emptyList(),
    val interceptors: List<RequestInterceptor> = emptyList()
)
