/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse

interface ListHandler {
    fun handleListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse

    fun handleListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse

    fun handleListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse

    fun handleListItemsCreatedEventRequest(alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>): AlexaResponse

    fun handleListItemsUpdatedEventRequest(alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>): AlexaResponse

    fun handleListItemsDeletedEventRequest(alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>): AlexaResponse
}
