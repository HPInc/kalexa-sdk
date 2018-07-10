package com.hp.kalexa.model.request.list

data class UpdateListRequest(
        val name: String,
        val state: ListState,
        val version: Long? = null)
