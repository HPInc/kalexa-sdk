/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.directive

interface DirectiveService {
    fun progressiveResponse(requestId: String, speechText: String)
}
