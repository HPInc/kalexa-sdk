/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.response.AlexaResponse

interface HelpIntentHandler : BaseHandler {

    /**
     * Handles Help Built In Intent coming from Alexa.
     */
    fun onHelpIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return IntentUtil.helpIntent()
    }
}
