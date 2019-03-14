/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.list

data class UpdateListItemRequest(
        val value: String,
        val status: ListItemState,
        val version: Long? = null)
