/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.viewport

import com.fasterxml.jackson.annotation.JsonProperty

data class Experience(
    @JsonProperty("arcMinuteHeight")
    val arcMinuteHeight: Int,
    @JsonProperty("arcMinuteWidth")
    val arcMinuteWidth: Int,
    @JsonProperty("canResize")
    val canResize: Boolean,
    @JsonProperty("canRotate")
    val canRotate: Boolean
)
