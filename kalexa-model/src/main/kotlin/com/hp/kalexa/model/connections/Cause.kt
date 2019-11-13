/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.connections

import com.fasterxml.jackson.annotation.JsonProperty

data class Cause(
    @JsonProperty("result")
    val result: Any?,
    @JsonProperty("status")
    val status: ConnectionStatus,
    @JsonProperty("token")
    val token: String,
    @JsonProperty("type")
    val type: String
)
