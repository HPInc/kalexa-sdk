/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.SessionResumedRequest
import com.hp.kalexa.model.response.AlexaResponse

interface RequesterHandler : BaseHandler {
    /**
     * Handles Session Resumed Request coming from Alexa. This is the result from a Provider skill
     * when using skill connections
     */
    fun onSessionResumedRequest(alexaRequest: AlexaRequest<SessionResumedRequest>): AlexaResponse
}
