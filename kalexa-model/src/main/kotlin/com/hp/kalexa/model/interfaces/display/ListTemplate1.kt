/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("ListTemplate1")
data class ListTemplate1(var backgroundImage: Image? = null,
                         var title: String = "",
                         var listItems: List<ListItem> = emptyList()) : Template()
