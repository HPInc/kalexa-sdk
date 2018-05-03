package com.hp.kalexa.core.model

import com.hp.kalexa.core.annotation.Helper
import com.hp.kalexa.core.annotation.Intents
import com.hp.kalexa.core.annotation.Launcher
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.IntentExecutor
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.alexaResponse

class IntentFake : IntentExecutor() {
    @Launcher
    override fun onLaunchIntent(request: LaunchRequest): AlexaResponse {
        return alexaResponse {
            response {
                speech {
                    "This is a hello from IntentFake@onCustomLaucnher"
                }
            }
        }
    }

    @Intents(intentNames = ["Teste", "Teste2", "Teste3"])
    override fun onIntentRequest(request: IntentRequest): AlexaResponse {
        return alexaResponse {
            response {
                speech {
                    "This is a hello from IntentFake@onCustomLaucnher"
                }
            }
        }
    }

    @Helper
    override fun onUnknownIntent(builtInIntent: BuiltInIntent): AlexaResponse {
        return super.onUnknownIntent(builtInIntent)
    }

}