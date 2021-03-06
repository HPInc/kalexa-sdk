/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.audioplayer

data class Stream(
    val expectedPreviousToken: String? = null,
    val token: String? = null,
    val url: String? = null,
    val offsetInMilliseconds: Long? = null
)
