/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.lwa.model

import com.fasterxml.jackson.annotation.JsonProperty

class Error(
    @JsonProperty("error")
    val error: String?,
    @JsonProperty("error_description")
    val errorDescription: String?
)
