package com.hp.kalexa.model.services.list

import com.hp.kalexa.model.request.list.*
import com.hp.kalexa.model.services.ServiceException

interface ListService {

    @Throws(ServiceException::class)
    fun getListsMetadata(token: String): AlexaListsMetadata

    @Throws(ServiceException::class)
    fun getList(listId: String, status: ListState, token: String): AlexaList

    @Throws(ServiceException::class)
    fun createList(request: CreateListRequest, token: String): AlexaListMetadata

    @Throws(ServiceException::class)
    fun updateList(listId: String, request: UpdateListRequest, token: String): AlexaListMetadata

    @Throws(ServiceException::class)
    fun deleteList(listId: String, token: String)

    @Throws(ServiceException::class)
    fun getListItem(listId: String, itemId: String, token: String): AlexaListItem

    @Throws(ServiceException::class)
    fun createListItem(listId: String, request: CreateListItemRequest, token: String): AlexaListItem

    @Throws(ServiceException::class)
    fun updateListItem(listId: String, itemId: String, request: UpdateListItemRequest, token: String): AlexaListItem

    @Throws(ServiceException::class)
    fun deleteListItem(listId: String, itemId: String, token: String)

}
