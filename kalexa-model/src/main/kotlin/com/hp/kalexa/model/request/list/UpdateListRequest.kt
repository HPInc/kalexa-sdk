/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.list

data class UpdateListRequest(
        val name: String,
        val state: ListState,
        val version: Long? = null)
