/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.interfaces.display.ListItem
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class ListItemsBuilder : ArrayList<ListItem>() {
    fun listItem(block: ListItem.() -> Unit) {
        add(ListItem().apply { block() })
    }
}
