package com.hp.kalexa.core.handler

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
        val applicationId = System.getenv("APPLICATION_ID")
        if (applicationId == null || applicationId.isEmpty()) {
            println("Application ID verification has been disabled, allowing request for all " + "application IDs")
            return true
        }
        val session = requestEnvelope.session
        val applicationIdIsMissingFromSession = (session.application?.applicationId == null)
        if (!applicationIdIsMissingFromSession) {
            /*
         * Note: we are still looking at the Session and not just the Context because some
         * clients may not yet be sending Context.
         */
            return applicationId == session.application?.applicationId
        }
        val context = requestEnvelope.context
        return if (context.system.application.applicationId == null) {
            false
        } else applicationId == context.system.application.applicationId
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