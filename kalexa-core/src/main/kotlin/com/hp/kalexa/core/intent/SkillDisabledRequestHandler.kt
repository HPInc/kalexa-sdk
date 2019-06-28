/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.skillevents.SkillDisabledRequest

interface SkillDisabledRequestHandler : BaseHandler {
    /**
     * Handles SkillDisabledRequest coming from Alexa
     */
    fun onSkillDisabledRequest(alexaRequest: AlexaRequest<SkillDisabledRequest>): AlexaResponse
}
