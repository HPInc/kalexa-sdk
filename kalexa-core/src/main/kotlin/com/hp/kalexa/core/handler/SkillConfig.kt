package com.hp.kalexa.core.handler

import com.hp.kalexa.core.intent.IntentHandler

data class SkillConfig(
    val intentHandlers: List<IntentHandler> = emptyList(),
    val interceptors: List<RequestInterceptor> = emptyList()
)
