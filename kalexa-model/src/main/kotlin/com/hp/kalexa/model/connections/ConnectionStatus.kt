/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */
package com.hp.kalexa.model.connections

import com.fasterxml.jackson.annotation.JsonProperty

data class ConnectionStatus(
    @JsonProperty("code")
    val code: String,
    @JsonProperty("message")
    val message: String
)
