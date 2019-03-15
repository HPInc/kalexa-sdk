/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.list

data class AlexaListMetadata(
    val listId: String = "",
    val name: String = "",
    val state: ListState? = null,
    val version: Long = 0,
    val statusMap: List<Status>? = emptyList())
