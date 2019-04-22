/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.directive

import com.hp.kalexa.model.services.BaseService

interface DirectiveService : BaseService {
    fun progressiveResponse(requestId: String, speechText: String, token: String)
}
