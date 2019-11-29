/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.handler.interceptor.InterceptorHandler
import com.hp.kalexa.core.interceptor.InterceptorException
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.json.JacksonSerializer
import com.hp.kalexa.model.request.AlexaRequest
import org.apache.logging.log4j.LogManager

class SpeechRequestHandler(
    private val skillConfig: SkillConfig = SkillConfig(),
    private val requestTypeDelegator: RequestTypeDelegator = RequestTypeDelegator(skillConfig.intentHandlers),
    private val interceptorHandler: InterceptorHandler = InterceptorHandler.newInstance(
        skillConfig.requestInterceptors,
        skillConfig.responseInterceptors
    )
) {
    private val logger = LogManager.getLogger(SpeechRequestHandler::class.java)

    fun process(input: ByteArray): String {
        val requestEnvelope = JacksonSerializer.deserialize(input, AlexaRequest::class.java)

        if (isApplicationIdValid(requestEnvelope).not()) {
            throw IllegalArgumentException("Request application ID doesn't match with given Application ID")
        }
        try {
            interceptorHandler.process(requestEnvelope)
        } catch (ex: InterceptorException) {
            logger.error(ex.message)
            return ex.responseCallback().toJson()
        }
        val alexaResponse = requestTypeDelegator.delegate(requestEnvelope)
        val response = try {
            interceptorHandler.process(alexaResponse)
        } catch (ex: InterceptorException) {
            logger.error(ex.message)
            alexaResponse
        }
        return response.toJson()
    }

    private fun isApplicationIdValid(alexaRequest: AlexaRequest<*>): Boolean {
        if (Util.isApplicationIdVerificationEnabled().not()) {
            // if application id is disabled, bypass validation.
            return true
        }
        val applicationId = Util.getApplicationID()
        if (applicationId == null || applicationId.isEmpty()) {
            logger.error("Application ID not defined in environment variable.")
            return false
        }
        return alexaRequest.session?.application?.applicationId?.let {
            applicationId == it
        } ?: alexaRequest.context.system.application.applicationId?.let {
            applicationId == it
        } ?: false
    }
}
