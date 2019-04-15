/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.response.AlexaResponse

interface LaunchRequestHandler : BaseHandler {

    /**
     * Handles Launch Intent Request coming from Alexa
     */
    fun onLaunchIntent(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse
}
