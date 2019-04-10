/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.interfaces.display.Image
import com.hp.kalexa.model.interfaces.display.ListItem
import com.hp.kalexa.model.interfaces.display.ListTemplate1
import com.hp.kalexa.model.interfaces.display.ListTemplate2
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class ListTemplateBuilder {
    var backgroundImage: Image? = null
    var title: String = ""
    var listItems = mutableListOf<ListItem>()

    fun listItems(block: ListItemsBuilder.() -> Unit) {
        listItems.addAll(ListItemsBuilder().apply { block() })
    }

    fun buildListTemplate1() = ListTemplate1(backgroundImage, title, listItems)

    fun buildListTemplate2() = ListTemplate2(backgroundImage, title, listItems)
}
