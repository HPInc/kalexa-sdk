/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.request.ConnectionsRequest
import com.hp.kalexa.model.request.ConnectionsResponseRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.InputHandlerEventRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.MessageReceivedRequest
import com.hp.kalexa.model.request.SessionEndedRequest
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

interface SpeechHandler {

    fun handleSessionStartedRequest(alexaRequest: AlexaRequest<SessionStartedRequest>): AlexaResponse

    fun handleLaunchRequest(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse

    fun handleIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse

    fun handleElementSelectedRequest(alexaRequest: AlexaRequest<ElementSelectedRequest>): AlexaResponse

    fun handleSessionEndedRequest(alexaRequest: AlexaRequest<SessionEndedRequest>): AlexaResponse

    fun handleCanFulfillIntentRequest(alexaRequest: AlexaRequest<CanFulfillIntentRequest>): AlexaResponse

    fun handleConnectionsResponseRequest(alexaRequest: AlexaRequest<ConnectionsResponseRequest>): AlexaResponse

    fun handleConnectionsRequest(alexaRequest: AlexaRequest<ConnectionsRequest>): AlexaResponse

    fun handleListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse

    fun handleListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse

    fun handleListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse

    fun handleListItemsCreatedEventRequest(alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>): AlexaResponse

    fun handleListItemsUpdatedEventRequest(alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>): AlexaResponse

    fun handleListItemsDeletedEventRequest(alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>): AlexaResponse

    fun handleReminderCreatedEventRequest(alexaRequest: AlexaRequest<ReminderCreatedEventRequest>): AlexaResponse

    fun handleReminderDeletedEventRequest(alexaRequest: AlexaRequest<ReminderDeletedEventRequest>): AlexaResponse

    fun handleReminderStartedEventRequest(alexaRequest: AlexaRequest<ReminderStartedEventRequest>): AlexaResponse

    fun handleReminderStatusChangedEventRequest(
        alexaRequest: AlexaRequest<ReminderStatusChangedEventRequest>
    ): AlexaResponse

    fun handleReminderUpdatedEventRequest(alexaRequest: AlexaRequest<ReminderUpdatedEventRequest>): AlexaResponse

    fun handleInputHandlerEventRequest(alexaRequest: AlexaRequest<InputHandlerEventRequest>): AlexaResponse

    fun handleMessageReceivedRequest(alexaRequest: AlexaRequest<MessageReceivedRequest>): AlexaResponse
    fun handleAccountLinkedRequest(alexaRequest: AlexaRequest<AccountLinkedRequest>): AlexaResponse

    fun handlePermissionAcceptedRequest(alexaRequest: AlexaRequest<PermissionAcceptedRequest>): AlexaResponse

    fun handleSubscriptionChangedRequest(alexaRequest: AlexaRequest<ProactiveSubscriptionChangedRequest>): AlexaResponse

    fun handleSkillDisabledRequest(alexaRequest: AlexaRequest<SkillDisabledRequest>): AlexaResponse

    fun handleSkillEnabledRequest(alexaRequest: AlexaRequest<SkillEnabledRequest>): AlexaResponse

    companion object {
        const val INTENT_CONTEXT = "com.hp.kalexa.intentContext"
        fun newInstance(intentHandlerInstances: List<BaseHandler> = emptyList()): SpeechHandler =
            ConcreteSpeechHandler(intentHandlerInstances)
    }
}
