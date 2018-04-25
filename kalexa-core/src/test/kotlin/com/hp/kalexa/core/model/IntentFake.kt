package com.hp.kalexa.core.model

import com.hp.kalexa.core.annotation.Helper
import com.hp.kalexa.core.annotation.Launcher
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.IntentExecutor
import com.hp.kalexa.model.AlexaResponse
import com.hp.kalexa.model.IntentRequest
import com.hp.kalexa.model.LaunchRequest
import com.hp.kalexa.model.alexaResponse

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

    override fun onIntentRequest(request: IntentRequest): AlexaResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Helper
    override fun onUnknownIntent(builtInIntent: BuiltInIntent): AlexaResponse {
        return super.onUnknownIntent(builtInIntent)
    }

}