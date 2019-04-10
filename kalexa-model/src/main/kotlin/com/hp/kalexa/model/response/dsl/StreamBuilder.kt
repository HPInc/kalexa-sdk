/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.interfaces.audioplayer.Stream

class StreamBuilder {
    var expectedPreviousToken: String = ""
    var token: String = ""
    var url: String = ""
    var offsetInMilliseconds: Long = 0

    fun build() = Stream(expectedPreviousToken, token, url, offsetInMilliseconds)
}
