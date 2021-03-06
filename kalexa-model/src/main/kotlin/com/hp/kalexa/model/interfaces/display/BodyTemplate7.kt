/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("BodyTemplate7")
data class BodyTemplate7(
    var title: String = "",
    var image: Image? = null,
    var backgroundImage: Image? = null
) : Template()
