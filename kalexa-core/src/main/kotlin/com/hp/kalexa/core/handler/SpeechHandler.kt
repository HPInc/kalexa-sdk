package com.hp.kalexa.core.handler

import com.hp.kalexa.model.*
import com.hp.kalexa.model.request.AlexaRequestEnvelope

interface SpeechHandler {

    fun handleSessionStartedRequest(envelope: AlexaRequestEnvelope<SessionStartedRequest>): AlexaResponse

    fun handleLaunchRequest(envelope: AlexaRequestEnvelope<LaunchRequest>): AlexaResponse

    fun handleIntentRequest(envelope: AlexaRequestEnvelope<IntentRequest>): AlexaResponse

    fun handleElementSelectedRequest(envelope: AlexaRequestEnvelope<ElementSelectedRequest>): AlexaResponse

    fun handleSessionEndedRequest(envelope: AlexaRequestEnvelope<SessionEndedRequest>): AlexaResponse

    fun handleLinkResultRequest(envelope: AlexaRequestEnvelope<LinkResultRequest>): AlexaResponse


    companion object {
        const val INTENT_CONTEXT = "com.hp.kotlinalexasdk.intentContext"
        const val SESSION_ID = "sessionId"
    }

}