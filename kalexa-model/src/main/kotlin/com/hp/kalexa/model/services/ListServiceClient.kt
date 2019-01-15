package com.hp.kalexa.model.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.hp.kalexa.model.request.list.*

import java.io.IOException

class ListServiceClient(private val client: ApiClient) : ListService {

    private val mapper: ObjectMapper = jacksonObjectMapper()

    @Throws(ServiceException::class)
    override fun getListsMetadata(token: String): AlexaListsMetadata {
        val uri = "$API_ENDPOINT/v2/householdlists/"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return if (response.responseCode in 200..299) {
                mapper.readValue(response.responseBody)
            } else {
                throw ServiceException("Got a non-successful response for lists metadata query")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve lists metadata", e)
        }
    }

    @Throws(ServiceException::class)
    override fun getList(listId: String, status: ListState, token: String): AlexaList {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/$status"
        try {
            val response = client.get(uri, getRequestHeaders(token))
            return if (response.responseCode in 200..299) {
                mapper.readValue(response.responseBody)
            } else {
                throw ServiceException("Got a non-successful response for list query")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve list", e)
        }
    }

    @Throws(ServiceException::class)
    override fun createList(request: CreateListRequest, token: String): AlexaListMetadata {
        val uri = "$API_ENDPOINT/v2/householdlists/"
        try {
            val response = client.post(uri, getRequestHeaders(token), mapper.writeValueAsString(request))
            return if (response.responseCode in 200..299) {
                mapper.readValue(response.responseBody)
            } else {
                throw ServiceException("Got a non-successful response for create list request")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to create list", e)
        }
    }

    @Throws(ServiceException::class)
    override fun updateList(listId: String, request: UpdateListRequest, token: String): AlexaListMetadata {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId"
        try {
            val response = client.put(uri, getRequestHeaders(token), mapper.writeValueAsString(request))
            return if (response.responseCode in 200..299) {
                mapper.readValue(response.responseBody)
            } else {
                throw ServiceException("Got a non-successful response for update list request")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to update list", e)
        }
    }

    @Throws(ServiceException::class)
    override fun deleteList(listId: String, token: String) {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId"
        try {
            val response = client.delete(uri, getRequestHeaders(token))
            if (response.responseCode < 200 || response.responseCode >= 300) {
                throw ServiceException("Got a non-successful response for delete list request")
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
            return if (response.responseCode in 200..299) {
                mapper.readValue(response.responseBody)
            } else {
                throw ServiceException("Got a non-successful response for list item query")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to retrieve list item", e)
        }
    }

    @Throws(ServiceException::class)
    override fun createListItem(listId: String, request: CreateListItemRequest, token: String): AlexaListItem {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/items"
        try {
            val response = client.post(uri, getRequestHeaders(token), mapper.writeValueAsString(request))
            return if (response.responseCode in 200..299) {
                mapper.readValue(response.responseBody)
            } else {
                throw ServiceException("Got a non-successful response for create list item request")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to create list item", e)
        }
    }

    @Throws(ServiceException::class)
    override fun updateListItem(listId: String, itemId: String, request: UpdateListItemRequest, token: String): AlexaListItem {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/items/$itemId"
        try {
            val response = client.put(uri, getRequestHeaders(token), mapper.writeValueAsString(request))
            return if (response.responseCode in 200..299) {
                mapper.readValue(response.responseBody)
            } else {
                throw ServiceException("Got a non-successful response for update list item request")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to update list item", e)
        }
    }

    @Throws(ServiceException::class)
    override fun deleteListItem(listId: String, itemId: String, token: String) {
        val uri = "$API_ENDPOINT/v2/householdlists/$listId/items/$itemId"
        try {
            val response = client.delete(uri, getRequestHeaders(token))
            if (response.responseCode < 200 || response.responseCode >= 300) {
                throw ServiceException("Got a non-successful response for delete list item request")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to delete list item", e)
        }
    }

    private fun getRequestHeaders(token: String): Map<String, String> {
        return mapOf(
                "Authorization" to "Bearer $token",
                "Content-Type" to "application/json"
        )
    }

    companion object {
        internal const val API_ENDPOINT = "https://api.amazonalexa.com"
    }
}
