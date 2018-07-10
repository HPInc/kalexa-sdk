package com.hp.kalexa.core.handler

import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.request.event.*
import com.hp.kalexa.model.response.AlexaResponse

interface SpeechHandler {

    fun handleSessionStartedRequest(envelope: AlexaRequestEnvelope<SessionStartedRequest>): AlexaResponse

    fun handleLaunchRequest(envelope: AlexaRequestEnvelope<LaunchRequest>): AlexaResponse

    fun handleIntentRequest(envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse

    fun handleElementSelectedRequest(envelope: AlexaRequestEnvelope<ElementSelectedRequest>): AlexaResponse

    fun handleSessionEndedRequest(envelope: AlexaRequestEnvelope<SessionEndedRequest>): AlexaResponse

    fun handleConnectionsResponseRequest(envelope: AlexaRequestEnvelope<ConnectionsResponseRequest>): AlexaResponse

    fun handleConnectionsRequest(envelope: AlexaRequestEnvelope<ConnectionsRequest>): AlexaResponse

    fun handleListCreatedEventRequest(envelope: AlexaRequestEnvelope<ListCreatedEventRequest>): AlexaResponse

    fun handleListUpdatedEventRequest(envelope: AlexaRequestEnvelope<ListUpdatedEventRequest>): AlexaResponse

    fun handleListDeletedEventRequest(envelope: AlexaRequestEnvelope<ListDeletedEventRequest>): AlexaResponse

    fun handleListItemsCreatedEventRequest(envelope: AlexaRequestEnvelope<ListItemsCreatedEventRequest>): AlexaResponse

    fun handleListItemsUpdatedEventRequest(envelope: AlexaRequestEnvelope<ListItemsUpdatedEventRequest>): AlexaResponse

    fun handleListItemsDeletedEventRequest(envelope: AlexaRequestEnvelope<ListItemsDeletedEventRequest>): AlexaResponse


    companion object {
        const val INTENT_CONTEXT = "com.hp.kalexa.intentContext"
        const val SESSION_ID = "sessionId"
    }

}