/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.list

import com.hp.kalexa.model.json.JacksonSerializer
import com.hp.kalexa.model.request.list.AlexaList
import com.hp.kalexa.model.request.list.AlexaListItem
import com.hp.kalexa.model.request.list.AlexaListMetadata
import com.hp.kalexa.model.request.list.AlexaListsMetadata
import com.hp.kalexa.model.request.list.CreateListItemRequest
import com.hp.kalexa.model.request.list.CreateListRequest
import com.hp.kalexa.model.request.list.ListState
import com.hp.kalexa.model.request.list.UpdateListItemRequest
import com.hp.kalexa.model.request.list.UpdateListRequest
import com.hp.kalexa.model.services.ApiClient
import com.hp.kalexa.model.services.BaseService.Companion.API_ENDPOINT
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException

class ListServiceClient(private val client: ApiClient = ApiClient()) : ListService {

    @Throws(ServiceException::class)
    override fun getListsMetadata(token: String): AlexaListsMetadata {
        val uri = "$API_ENDPOINT/v2/householdlists/"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve lists metadata", e)
        }
    }

    @Throws(ServiceException::class)
    override fun getList(listId: String, status: ListState, token: String): AlexaList {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/$status"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve list", e)
        }
    }

    @Throws(ServiceException::class)
    override fun createList(request: CreateListRequest, token: String): AlexaListMetadata {
        val uri = "$API_ENDPOINT/v2/householdlists/"
        try {
            val response = client.post(uri, getRequestHeaders(token), JacksonSerializer.serialize(request))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to create list", e)
        }
    }

    @Throws(ServiceException::class)
    override fun updateList(listId: String, request: UpdateListRequest, token: String): AlexaListMetadata {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId"
        try {
            val response = client.put(uri, getRequestHeaders(token), JacksonSerializer.serialize(request))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to update list", e)
        }
    }

    @Throws(ServiceException::class)
    override fun deleteList(listId: String, token: String) {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId"
        try {
            val response = client.delete(uri, getRequestHeaders(token))
            if (response.responseCode !in ApiClient.SUCCESS_CODE_RANGE) {
                throw ServiceException(response.responseBody)
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to delete list", e)
        }
    }

    @Throws(ServiceException::class)
    override fun getListItem(listId: String, itemId: String, token: String): AlexaListItem {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/items/$itemId"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve list item", e)
        }
    }

    @Throws(ServiceException::class)
    override fun createListItem(listId: String, request: CreateListItemRequest, token: String): AlexaListItem {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/items"
        try {
            val response = client.post(uri, getRequestHeaders(token), JacksonSerializer.serialize(request))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to create list item", e)
        }
    }

    @Throws(ServiceException::class)
    override fun updateListItem(
        listId: String,
        itemId: String,
        request: UpdateListItemRequest,
        token: String
    ): AlexaListItem {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/items/$itemId"
        try {
            val response = client.put(uri, getRequestHeaders(token), JacksonSerializer.serialize(request))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to update list item", e)
        }
    }

    @Throws(ServiceException::class)
    override fun deleteListItem(listId: String, itemId: String, token: String) {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/items/$itemId"
        try {
            val response = client.delete(uri, getRequestHeaders(token))
            if (response.responseCode !in ApiClient.SUCCESS_CODE_RANGE) {
                throw ServiceException(response.responseBody)
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to delete list item", e)
        }
    }
}
