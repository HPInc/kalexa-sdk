/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.ConnectionsRequest
import com.hp.kalexa.model.response.AlexaResponse

interface ProviderHandler : BaseHandler {

    /**
     * Handles Connections Request coming from Alexa. This is the request from a requester skill when using
     * skill connections.
     * Should be used combined with @Provider annotation
     */
    fun onConnectionsRequest(alexaRequest: AlexaRequest<ConnectionsRequest>): AlexaResponse
}
