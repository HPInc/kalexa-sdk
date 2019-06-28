/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request.event.reminder

data class SpokenText(
    var locale: String? = null,
    var ssml: String? = null,
    var text: String? = null
)
