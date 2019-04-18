/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse

interface ListEventsHandler : BaseHandler {

    fun onListItemsUpdatedEventRequest(alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListItemsDeletedEventRequest(alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListItemsCreatedEventRequest(alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }

    fun onListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }
}
