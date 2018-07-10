package com.hp.kalexa.model.request.list

data class CreateListItemRequest(val value: String,
                                 val status: ListItemState)
