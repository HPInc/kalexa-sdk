/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.connections

import com.fasterxml.jackson.annotation.JsonProperty

data class Task(
    @JsonProperty("input")
    val input: Input,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("version")
    val version: String
)
