/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.interceptor.InterceptorException
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.json.JacksonSerializer
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.InputHandlerEventRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.MessageReceivedRequest
import com.hp.kalexa.model.request.SessionEndedRequest
import com.hp.kalexa.model.request.SessionResumedRequest
import com.hp.kalexa.model.request.SessionStartedRequest
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderCreatedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderDeletedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderStartedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderStatusChangedEventRequest
import com.hp.kalexa.model.request.event.reminder.ReminderUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.skillevents.AccountLinkedRequest
import com.hp.kalexa.model.skillevents.PermissionAcceptedRequest
import com.hp.kalexa.model.skillevents.ProactiveSubscriptionChangedRequest
import com.hp.kalexa.model.skillevents.SkillDisabledRequest
import com.hp.kalexa.model.skillevents.SkillEnabledRequest
import org.apache.logging.log4j.LogManager

class SpeechRequestHandler(
    private val skillConfig: SkillConfig = SkillConfig(),
    private val speechHandler: SpeechHandler = SpeechHandler.newInstance(skillConfig.intentHandlers),
    private val interceptorHandler: InterceptorHandler = InterceptorHandler.newInstance(skillConfig.interceptors)
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
        return handleRequestType(requestEnvelope)
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

    @Suppress("unchecked_cast")
    private fun handleRequestType(alexaRequest: AlexaRequest<*>): String {
        val alexaResponse = when (alexaRequest.request) {
            is SessionStartedRequest ->
                speechHandler.handleSessionStartedRequest(alexaRequest.cast())
            is LaunchRequest ->
                speechHandler.handleLaunchRequest(alexaRequest.cast())
            is IntentRequest ->
                speechHandler.handleIntentRequest(alexaRequest.cast())
            is SessionResumedRequest ->
                speechHandler.handleSessionResumedRequest(alexaRequest.cast())
            is CanFulfillIntentRequest ->
                speechHandler.handleCanFulfillIntentRequest(alexaRequest.cast())
            is SessionEndedRequest ->
                speechHandler.handleSessionEndedRequest(alexaRequest.cast())
            is ElementSelectedRequest ->
                speechHandler.handleElementSelectedRequest(alexaRequest.cast())
            is ListCreatedEventRequest ->
                speechHandler.handleListCreatedEventRequest(alexaRequest.cast())
            is ListUpdatedEventRequest ->
                speechHandler.handleListUpdatedEventRequest(alexaRequest.cast())
            is ListDeletedEventRequest ->
                speechHandler.handleListDeletedEventRequest(alexaRequest.cast())
            is ListItemsCreatedEventRequest ->
                speechHandler.handleListItemsCreatedEventRequest(alexaRequest.cast())
            is ListItemsUpdatedEventRequest ->
                speechHandler.handleListItemsUpdatedEventRequest(alexaRequest.cast())
            is ListItemsDeletedEventRequest ->
                speechHandler.handleListItemsDeletedEventRequest(alexaRequest.cast())
            is ReminderCreatedEventRequest ->
                speechHandler.handleReminderCreatedEventRequest(alexaRequest.cast())
            is ReminderStartedEventRequest ->
                speechHandler.handleReminderStartedEventRequest(alexaRequest.cast())
            is ReminderUpdatedEventRequest ->
                speechHandler.handleReminderUpdatedEventRequest(alexaRequest.cast())
            is ReminderDeletedEventRequest ->
                speechHandler.handleReminderDeletedEventRequest(alexaRequest.cast())
            is ReminderStatusChangedEventRequest ->
                speechHandler.handleReminderStatusChangedEventRequest(alexaRequest.cast())
            is InputHandlerEventRequest ->
                speechHandler.handleInputHandlerEventRequest(alexaRequest.cast())
            is MessageReceivedRequest ->
                speechHandler.handleMessageReceivedRequest(alexaRequest.cast())
            is AccountLinkedRequest ->
                speechHandler.handleAccountLinkedRequest(alexaRequest.cast())
            is PermissionAcceptedRequest ->
                speechHandler.handlePermissionAcceptedRequest(alexaRequest.cast())
            is ProactiveSubscriptionChangedRequest ->
                speechHandler.handleSubscriptionChangedRequest(alexaRequest.cast())
            is SkillDisabledRequest ->
                speechHandler.handleSkillDisabledRequest(alexaRequest.cast())
            is SkillEnabledRequest ->
                speechHandler.handleSkillEnabledRequest(alexaRequest.cast())
            else -> AlexaResponse.emptyResponse()
        }
        return alexaResponse.toJson()
    }
}
