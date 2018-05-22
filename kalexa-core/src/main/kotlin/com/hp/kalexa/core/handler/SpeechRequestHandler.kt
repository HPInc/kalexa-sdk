package com.hp.kalexa.core.handler

import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.response.AlexaResponse

class SpeechRequestHandler(private val speechHandler: SpeechHandler) {

    fun process(input: ByteArray): String {
        val requestEnvelope = AlexaRequestEnvelope.fromJson(input)

        if (validateApplicationId(requestEnvelope).not()) {
            throw IllegalArgumentException("Request application ID doesn't match with given Application ID")
        }

        return handleRequestType(requestEnvelope)
    }

    private fun validateApplicationId(requestEnvelope: AlexaRequestEnvelope<*>): Boolean {
        val applicationId = Util.getApplicationID()
        if (applicationId == null || applicationId.isEmpty()) {
            println("Application ID not defined in environment variable.")
            return false
        }
        requestEnvelope.session.application?.applicationId?.let {
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
            else -> AlexaResponse.emptyResponse()
        }
        return alexaResponse.toJson()
    }
}