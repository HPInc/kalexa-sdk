/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.directive

import com.fasterxml.jackson.annotation.JsonProperty

data class Header(
    @JsonProperty("requestId")
    val requestId: String
)
