/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("ListTemplate2")
data class ListTemplate2(
    var backgroundImage: Image? = null,
    var title: String = "",
    var listItems: List<ListItem> = emptyList()) : Template()
