/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.ConnectionsResponseRequest
import com.hp.kalexa.model.response.AlexaResponse

interface RequesterHandler : BaseHandler {
    /**
     * Handles Connections Response Request coming from Alexa. This is the result from a Provider skill
     * when using skill connections
     * Should be used combined with @Requester annotation
     */
    fun onConnectionsResponse(alexaRequest: AlexaRequest<ConnectionsResponseRequest>): AlexaResponse
}
