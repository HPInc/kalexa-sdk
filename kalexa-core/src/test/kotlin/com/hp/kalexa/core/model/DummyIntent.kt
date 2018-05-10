package com.hp.kalexa.core.model

import com.hp.kalexa.core.intent.IntentExecutor
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.alexaResponse

class DummyIntent : IntentExecutor() {

    override fun onYesIntent(request: IntentRequest): AlexaResponse {
        return alexaResponse { response { speech { "Yes Intent" } } }
    }

    override fun onNoIntent(request: IntentRequest): AlexaResponse {
        return alexaResponse { response { speech { "No Intent" } } }
    }

    override fun onStopIntent(request: IntentRequest): AlexaResponse {
        return alexaResponse { response { speech { "Stop Intent" } } }
    }

    override fun onHelpIntent(request: IntentRequest): AlexaResponse {
        return alexaResponse { response { speech { "Help Intent" } } }
    }

    override fun onCancelIntent(request: IntentRequest): AlexaResponse {
        return alexaResponse { response { speech { "Cancel Intent" } } }
    }

}