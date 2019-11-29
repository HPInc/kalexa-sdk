/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.skillevents.AccountLinkedRequest
import com.hp.kalexa.model.skillevents.PermissionAcceptedRequest
import com.hp.kalexa.model.skillevents.ProactiveSubscriptionChangedRequest
import com.hp.kalexa.model.skillevents.SkillDisabledRequest
import com.hp.kalexa.model.skillevents.SkillEnabledRequest

interface SkillEventsHandler {
    fun handleAccountLinkedRequest(alexaRequest: AlexaRequest<AccountLinkedRequest>): AlexaResponse

    fun handlePermissionAcceptedRequest(alexaRequest: AlexaRequest<PermissionAcceptedRequest>): AlexaResponse

    fun handleSubscriptionChangedRequest(alexaRequest: AlexaRequest<ProactiveSubscriptionChangedRequest>): AlexaResponse

    fun handleSkillDisabledRequest(alexaRequest: AlexaRequest<SkillDisabledRequest>): AlexaResponse

    fun handleSkillEnabledRequest(alexaRequest: AlexaRequest<SkillEnabledRequest>): AlexaResponse
}
