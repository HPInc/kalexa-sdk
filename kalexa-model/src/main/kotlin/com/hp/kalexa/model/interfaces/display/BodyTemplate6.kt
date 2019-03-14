/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("BodyTemplate6")
data class BodyTemplate6(var backgroundImage: Image? = null,
                         var textContent: TextContent? = null,
                         var image: Image? = null) : Template()