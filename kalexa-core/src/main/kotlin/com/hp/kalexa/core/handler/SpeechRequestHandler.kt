/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.json.JacksonSerializer
import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse
import org.apache.logging.log4j.LogManager

class SpeechRequestHandler(private val skillConfig: SkillConfig) {
    private val logger = LogManager.getLogger(SpeechRequestHandler::class.java)
    private val speechHandler: SpeechHandler = SpeechHandler.newInstance(skillConfig.intentHandlers)

    fun process(input: ByteArray): String {
        val requestEnvelope = JacksonSerializer.deserialize(input, AlexaRequest::class.java)

        if (validateApplicationId(requestEnvelope).not()) {
            throw IllegalArgumentException("Request application ID doesn't match with given Application ID")
        }
        handleInterceptors(skillConfig.interceptors as List<RequestInterceptor<Request>>, requestEnvelope)
        return handleRequestType(requestEnvelope)
    }

    private fun handleInterceptors(requestInterceptors: List<RequestInterceptor<Request>>, requestEnvelope: AlexaRequest<*>) {
        for (interceptor in requestInterceptors) {
            interceptor.intercept(requestEnvelope)
        }
    }

    private fun validateApplicationId(alexaRequest: AlexaRequest<*>): Boolean {
        return true

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

    @Suppress("unchecked_cast")
    private fun handleRequestType(alexaRequest: AlexaRequest<*>): String {
        val alexaResponse = when (alexaRequest.request) {
            is SessionStartedRequest ->
                speechHandler.handleSessionStartedRequest(alexaRequest as AlexaRequest<SessionStartedRequest>)
            is LaunchRequest ->
                speechHandler.handleLaunchRequest(alexaRequest as AlexaRequest<LaunchRequest>)
            is IntentRequest ->
                speechHandler.handleIntentRequest(alexaRequest as AlexaRequest<IntentRequest>)
            is ConnectionsResponseRequest ->
                speechHandler.handleConnectionsResponseRequest(alexaRequest as AlexaRequest<ConnectionsResponseRequest>)
            is ConnectionsRequest ->
                speechHandler.handleConnectionsRequest(alexaRequest as AlexaRequest<ConnectionsRequest>)
            is CanFulfillIntentRequest ->
                speechHandler.handleCanFulfillIntentRequest(alexaRequest as AlexaRequest<CanFulfillIntentRequest>)
            is SessionEndedRequest ->
                speechHandler.handleSessionEndedRequest(alexaRequest as AlexaRequest<SessionEndedRequest>)
            is ElementSelectedRequest ->
                speechHandler.handleElementSelectedRequest(alexaRequest as AlexaRequest<ElementSelectedRequest>)
            is ListCreatedEventRequest ->
                speechHandler.handleListCreatedEventRequest(alexaRequest as AlexaRequest<ListCreatedEventRequest>)
            is ListUpdatedEventRequest ->
                speechHandler.handleListUpdatedEventRequest(alexaRequest as AlexaRequest<ListUpdatedEventRequest>)
            is ListDeletedEventRequest ->
                speechHandler.handleListDeletedEventRequest(alexaRequest as AlexaRequest<ListDeletedEventRequest>)
            is ListItemsCreatedEventRequest ->
                speechHandler.handleListItemsCreatedEventRequest(
                        alexaRequest as AlexaRequest<ListItemsCreatedEventRequest>)
            is ListItemsUpdatedEventRequest ->
                speechHandler.handleListItemsUpdatedEventRequest(
                        alexaRequest as AlexaRequest<ListItemsUpdatedEventRequest>)
            is ListItemsDeletedEventRequest ->
                speechHandler.handleListItemsDeletedEventRequest(
                        alexaRequest as AlexaRequest<ListItemsDeletedEventRequest>)
            else -> AlexaResponse.emptyResponse()
        }
        return alexaResponse.toJson()
    }
}
