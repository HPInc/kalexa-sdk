/*
 * Copyright 2016-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 * this file except in compliance with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hp.kalexa.model.services;


import com.hp.kalexa.model.request.list.*;

public interface ListService {

    AlexaListsMetadata getListsMetadata(String token) throws ServiceException;

    AlexaList getList(String listId, ListState status, String token) throws ServiceException;

    AlexaListMetadata createList(CreateListRequest request, String token) throws ServiceException;

    AlexaListMetadata updateList(String listId, UpdateListRequest request, String token) throws ServiceException;

    void deleteList(String listId, String token) throws ServiceException;

    AlexaListItem getListItem(String listId, String itemId, String token) throws ServiceException;

    AlexaListItem createListItem(String listId, CreateListItemRequest request, String token) throws ServiceException;

    AlexaListItem updateListItem(String listId, String itemId, UpdateListItemRequest request, String token) throws ServiceException;

    void deleteListItem(String listId, String itemId, String token) throws ServiceException;

}
