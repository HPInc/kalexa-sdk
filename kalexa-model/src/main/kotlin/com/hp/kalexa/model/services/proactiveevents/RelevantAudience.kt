/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents

import com.hp.kalexa.model.services.proactiveevents.enums.RelevantAudienceType

data class RelevantAudience(
    var type: RelevantAudienceType? = null,
    var payload: Map<String, Any>? = null
)
