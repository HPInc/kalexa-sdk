/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

data class Image(val contentDescription: String = "",
                 val sources: List<ImageInstance> = emptyList())