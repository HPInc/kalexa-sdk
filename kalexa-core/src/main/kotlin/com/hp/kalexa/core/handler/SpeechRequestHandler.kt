package com.hp.kalexa.core.handler

import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.json.JacksonSerializer
import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.request.event.*
import com.hp.kalexa.model.response.AlexaResponse
import org.apache.logging.log4j.LogManager

class SpeechRequestHandler(private val speechHandler: SpeechHandler) {
    private val logger = LogManager.getLogger(SpeechRequestHandler::class.java)

    fun process(input: ByteArray): String {
        val requestEnvelope = JacksonSerializer.deserialize(input, AlexaRequestEnvelope::class.java)

        if (validateApplicationId(requestEnvelope).not()) {
            throw IllegalArgumentException("Request application ID doesn't match with given Application ID")
        }

        return handleRequestType(requestEnvelope)
    }

    private fun validateApplicationId(requestEnvelope: AlexaRequestEnvelope<*>): Boolean {
        val applicationId = Util.getApplicationID()
        if (applicationId == null || applicationId.isEmpty()) {
            logger.error("Application ID not defined in environment variable.")
            return false
        }
        requestEnvelope.session?.application?.applicationId?.let {
            return applicationId == it
        }
        return requestEnvelope.context.system.application.applicationId?.let {
            applicationId == it
        } ?: false
    }

    @Suppress("unchecked_cast")
    private fun handleRequestType(alexaRequest: AlexaRequestEnvelope<*>): String {
        val alexaResponse = when (alexaRequest.request) {
            is SessionStartedRequest ->
                speechHandler.handleSessionStartedRequest(alexaRequest as AlexaRequestEnvelope<SessionStartedRequest>)
            is LaunchRequest ->
                speechHandler.handleLaunchRequest(alexaRequest as AlexaRequestEnvelope<LaunchRequest>)
            is IntentRequest ->
                speechHandler.handleIntentRequest(alexaRequest as AlexaRequestEnvelope<IntentRequest>)
            is ConnectionsResponseRequest ->
                speechHandler.handleConnectionsResponseRequest(alexaRequest as AlexaRequestEnvelope<ConnectionsResponseRequest>)
            is ConnectionsRequest ->
                speechHandler.handleConnectionsRequest(alexaRequest as AlexaRequestEnvelope<ConnectionsRequest>)
            is SessionEndedRequest ->
                speechHandler.handleSessionEndedRequest(alexaRequest as AlexaRequestEnvelope<SessionEndedRequest>)
            is ElementSelectedRequest ->
                speechHandler.handleElementSelectedRequest(alexaRequest as AlexaRequestEnvelope<ElementSelectedRequest>)
            is ListCreatedEventRequest ->
                speechHandler.handleListCreatedEventRequest(alexaRequest as AlexaRequestEnvelope<ListCreatedEventRequest>)
            is ListUpdatedEventRequest ->
                speechHandler.handleListUpdatedEventRequest(alexaRequest as AlexaRequestEnvelope<ListUpdatedEventRequest>)
            is ListDeletedEventRequest ->
                speechHandler.handleListDeletedEventRequest(alexaRequest as AlexaRequestEnvelope<ListDeletedEventRequest>)
            is ListItemsCreatedEventRequest ->
                speechHandler.handleListItemsCreatedEventRequest(alexaRequest as AlexaRequestEnvelope<ListItemsCreatedEventRequest>)
            is ListItemsUpdatedEventRequest ->
                speechHandler.handleListItemsUpdatedEventRequest(alexaRequest as AlexaRequestEnvelope<ListItemsUpdatedEventRequest>)
            is ListItemsDeletedEventRequest ->
                speechHandler.handleListItemsDeletedEventRequest(alexaRequest as AlexaRequestEnvelope<ListItemsDeletedEventRequest>)
            else -> AlexaResponse.emptyResponse()
        }
        return alexaResponse.toJson()
    }
}