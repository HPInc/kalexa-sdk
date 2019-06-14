/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.list

import com.hp.kalexa.model.request.list.AlexaList
import com.hp.kalexa.model.request.list.AlexaListItem
import com.hp.kalexa.model.request.list.AlexaListMetadata
import com.hp.kalexa.model.request.list.AlexaListsMetadata
import com.hp.kalexa.model.request.list.CreateListItemRequest
import com.hp.kalexa.model.request.list.CreateListRequest
import com.hp.kalexa.model.request.list.ListState
import com.hp.kalexa.model.request.list.UpdateListItemRequest
import com.hp.kalexa.model.request.list.UpdateListRequest
import com.hp.kalexa.model.services.ServiceException

interface ListService {

    @Throws(ServiceException::class)
    fun getListsMetadata(): AlexaListsMetadata

    @Throws(ServiceException::class)
    fun getList(listId: String, status: ListState): AlexaList

    @Throws(ServiceException::class)
    fun createList(request: CreateListRequest): AlexaListMetadata

    @Throws(ServiceException::class)
    fun updateList(listId: String, request: UpdateListRequest): AlexaListMetadata

    @Throws(ServiceException::class)
    fun deleteList(listId: String)

    @Throws(ServiceException::class)
    fun getListItem(listId: String, itemId: String): AlexaListItem

    @Throws(ServiceException::class)
    fun createListItem(listId: String, request: CreateListItemRequest): AlexaListItem

    @Throws(ServiceException::class)
    fun updateListItem(listId: String, itemId: String, request: UpdateListItemRequest): AlexaListItem

    @Throws(ServiceException::class)
    fun deleteListItem(listId: String, itemId: String)
}
