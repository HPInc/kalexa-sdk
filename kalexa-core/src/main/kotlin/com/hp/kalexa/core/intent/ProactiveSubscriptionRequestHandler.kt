/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.skillevents.ProactiveSubscriptionChangedRequest

interface ProactiveSubscriptionRequestHandler : BaseHandler {
    /**
     * Handles Proactive Subscription Request coming from Alexa
     */
    fun onProactiveSubscriptionRequest(alexaRequest: AlexaRequest<ProactiveSubscriptionChangedRequest>): AlexaResponse
}
