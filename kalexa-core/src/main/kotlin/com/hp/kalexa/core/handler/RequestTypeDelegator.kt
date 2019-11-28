/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.handler.requesthandler.BasicHandler
import com.hp.kalexa.core.handler.requesthandler.DefaultBasicHandler
import com.hp.kalexa.core.handler.requesthandler.DefaultGameEngineRequestHandler
import com.hp.kalexa.core.handler.requesthandler.DefaultListHandler
import com.hp.kalexa.core.handler.requesthandler.DefaultMessagingRequestHandler
import com.hp.kalexa.core.handler.requesthandler.DefaultReminderHandler
import com.hp.kalexa.core.handler.requesthandler.DefaultSkillEventsHandler
import com.hp.kalexa.core.handler.requesthandler.GameEngineRequestHandler
import com.hp.kalexa.core.handler.requesthandler.ListHandler
import com.hp.kalexa.core.handler.requesthandler.MessagingRequestHandler
import com.hp.kalexa.core.handler.requesthandler.ReminderHandler
import com.hp.kalexa.core.handler.requesthandler.SkillEventsHandler
import com.hp.kalexa.core.intent.BaseHandler
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

class RequestTypeDelegator(
    intentHandlers: List<BaseHandler> = emptyList(),
    private val repository: BaseHandlerRepository = BaseHandlerRepository(intentHandlers),
    private val listHandler: ListHandler = DefaultListHandler(
        repository
    ),
    private val reminderHandler: ReminderHandler = DefaultReminderHandler(
        repository
    ),
    private val basicHandler: BasicHandler = DefaultBasicHandler(
        repository
    ),
    private val skillEventsHandler: SkillEventsHandler = DefaultSkillEventsHandler(
        repository
    ),
    private val messagingRequestHandler: MessagingRequestHandler = DefaultMessagingRequestHandler(
        repository
    ),
    private val gameEngineRequestHandler: GameEngineRequestHandler = DefaultGameEngineRequestHandler(
        repository
    )
) {

    @Suppress("unchecked_cast")
    fun delegate(alexaRequest: AlexaRequest<*>): AlexaResponse {
        return when (alexaRequest.request) {
            is SessionStartedRequest -> basicHandler.handleSessionStartedRequest(alexaRequest.cast())
            is LaunchRequest -> basicHandler.handleLaunchRequest(alexaRequest.cast())
            is IntentRequest -> basicHandler.handleIntentRequest(alexaRequest.cast())
            is SessionResumedRequest -> basicHandler.handleSessionResumedRequest(alexaRequest.cast())
            is CanFulfillIntentRequest -> basicHandler.handleCanFulfillIntentRequest(alexaRequest.cast())
            is SessionEndedRequest -> basicHandler.handleSessionEndedRequest(alexaRequest.cast())
            is ElementSelectedRequest -> basicHandler.handleElementSelectedRequest(alexaRequest.cast())
            is InputHandlerEventRequest -> gameEngineRequestHandler.handleInputHandlerEventRequest(alexaRequest.cast())
            is MessageReceivedRequest -> messagingRequestHandler.handleMessageReceivedRequest(alexaRequest.cast())
            is AccountLinkedRequest -> skillEventsHandler.handleAccountLinkedRequest(alexaRequest.cast())
            is PermissionAcceptedRequest -> skillEventsHandler.handlePermissionAcceptedRequest(alexaRequest.cast())
            is ProactiveSubscriptionChangedRequest -> skillEventsHandler.handleSubscriptionChangedRequest(alexaRequest.cast())
            is SkillDisabledRequest -> skillEventsHandler.handleSkillDisabledRequest(alexaRequest.cast())
            is SkillEnabledRequest -> skillEventsHandler.handleSkillEnabledRequest(alexaRequest.cast())
            is ListCreatedEventRequest -> listHandler.handleListCreatedEventRequest(alexaRequest.cast())
            is ListUpdatedEventRequest -> listHandler.handleListUpdatedEventRequest(alexaRequest.cast())
            is ListDeletedEventRequest -> listHandler.handleListDeletedEventRequest(alexaRequest.cast())
            is ListItemsCreatedEventRequest -> listHandler.handleListItemsCreatedEventRequest(alexaRequest.cast())
            is ListItemsUpdatedEventRequest -> listHandler.handleListItemsUpdatedEventRequest(alexaRequest.cast())
            is ListItemsDeletedEventRequest -> listHandler.handleListItemsDeletedEventRequest(alexaRequest.cast())
            is ReminderCreatedEventRequest -> reminderHandler.handleReminderCreatedEventRequest(alexaRequest.cast())
            is ReminderStartedEventRequest -> reminderHandler.handleReminderStartedEventRequest(alexaRequest.cast())
            is ReminderUpdatedEventRequest -> reminderHandler.handleReminderUpdatedEventRequest(alexaRequest.cast())
            is ReminderDeletedEventRequest -> reminderHandler.handleReminderDeletedEventRequest(alexaRequest.cast())
            is ReminderStatusChangedEventRequest -> reminderHandler.handleReminderStatusChangedEventRequest(alexaRequest.cast())
            else -> AlexaResponse.emptyResponse()
        }
    }
}
