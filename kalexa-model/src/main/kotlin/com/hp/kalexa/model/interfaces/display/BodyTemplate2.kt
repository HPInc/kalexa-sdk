/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("BodyTemplate2")
data class BodyTemplate2(
    var backgroundImage: Image? = null,
    var image: Image? = null,
    var title: String = "",
    var textContent: TextContent? = null
) : Template()