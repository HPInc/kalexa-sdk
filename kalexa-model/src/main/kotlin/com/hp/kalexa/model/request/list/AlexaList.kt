package com.hp.kalexa.model.request.list

data class AlexaList(
        var listId: String = "",
        var name: String = "",
        var state: ListState? = null,
        var version: Long = 0,
        var items: List<AlexaListItem> = emptyList(),
        var links: Links? = null)