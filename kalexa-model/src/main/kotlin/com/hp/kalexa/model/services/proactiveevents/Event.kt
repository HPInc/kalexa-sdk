/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents

data class Event(
    private var name: String? = null,
    private var payload: Map<String, Any>? = null
)
