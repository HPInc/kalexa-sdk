package com.hp.kalexa.model.request.list

data class AlexaListItem(
        val id: String = "",
        val version: Long = 0,
        val value: String = "",
        val status: ListItemState? = null,
        val createdTime: String = "",
        val updatedTime: String = "",
        val href: String = "")
