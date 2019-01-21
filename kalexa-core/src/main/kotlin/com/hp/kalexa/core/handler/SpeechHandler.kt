package com.hp.kalexa.core.handler

import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.request.event.*
import com.hp.kalexa.model.response.AlexaResponse

interface SpeechHandler {

    fun handleSessionStartedRequest(alexaRequest: AlexaRequest<SessionStartedRequest>): AlexaResponse

    fun handleLaunchRequest(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse

    fun handleIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse

    fun handleElementSelectedRequest(alexaRequest: AlexaRequest<ElementSelectedRequest>): AlexaResponse

    fun handleSessionEndedRequest(alexaRequest: AlexaRequest<SessionEndedRequest>): AlexaResponse

    fun handleConnectionsResponseRequest(alexaRequest: AlexaRequest<ConnectionsResponseRequest>): AlexaResponse

    fun handleConnectionsRequest(alexaRequest: AlexaRequest<ConnectionsRequest>): AlexaResponse

    fun handleListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse

    fun handleListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse

    fun handleListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse

    fun handleListItemsCreatedEventRequest(alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>): AlexaResponse

    fun handleListItemsUpdatedEventRequest(alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>): AlexaResponse

    fun handleListItemsDeletedEventRequest(alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>): AlexaResponse


    companion object {
        const val INTENT_CONTEXT = "com.hp.kalexa.intentContext"
    }

}