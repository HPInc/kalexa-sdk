/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.core.extension.cast
import com.hp.kalexa.core.handler.BaseHandlerRepository
import com.hp.kalexa.core.intent.AccountLinkedRequestHandler
import com.hp.kalexa.core.intent.PermissionAcceptedRequestHandler
import com.hp.kalexa.core.intent.ProactiveSubscriptionRequestHandler
import com.hp.kalexa.core.intent.SkillDisabledRequestHandler
import com.hp.kalexa.core.intent.SkillEnabledRequestHandler
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.skillevents.AccountLinkedRequest
import com.hp.kalexa.model.skillevents.PermissionAcceptedRequest
import com.hp.kalexa.model.skillevents.ProactiveSubscriptionChangedRequest
import com.hp.kalexa.model.skillevents.SkillDisabledRequest
import com.hp.kalexa.model.skillevents.SkillEnabledRequest
import org.apache.logging.log4j.LogManager

class DefaultSkillEventsHandler(repository: BaseHandlerRepository) : SkillEventsHandler,
    AbstractRequestHandler(repository) {
    private val logger = LogManager.getLogger(DefaultSkillEventsHandler::class.java)

    override fun handleAccountLinkedRequest(alexaRequest: AlexaRequest<AccountLinkedRequest>): AlexaResponse {
        logger.info("=========================== AccountLinkedRequest =========================")
        val inputHandler: AccountLinkedRequestHandler? = getHandler(AccountLinkedRequestHandler::class)?.cast()
        return inputHandler?.let {
            val alexaResponse = inputHandler.onAccountLinkedRequest(alexaRequest)
            return generateResponse(inputHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handlePermissionAcceptedRequest(alexaRequest: AlexaRequest<PermissionAcceptedRequest>): AlexaResponse {
        logger.info("=========================== PermissionAcceptedRequest =========================")
        val inputHandler: PermissionAcceptedRequestHandler? =
            getHandler(PermissionAcceptedRequestHandler::class)?.cast()
        return inputHandler?.let {
            val alexaResponse = inputHandler.onPermissionAcceptedRequest(alexaRequest)
            return generateResponse(inputHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleSubscriptionChangedRequest(
        alexaRequest: AlexaRequest<ProactiveSubscriptionChangedRequest>
    ): AlexaResponse {
        logger.info("=========================== ProactiveSubscriptionChangedRequest =========================")
        val inputHandler: ProactiveSubscriptionRequestHandler? =
            getHandler(ProactiveSubscriptionRequestHandler::class)?.cast()
        return inputHandler?.let {
            val alexaResponse = inputHandler.onProactiveSubscriptionRequest(alexaRequest)
            return generateResponse(inputHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleSkillDisabledRequest(alexaRequest: AlexaRequest<SkillDisabledRequest>): AlexaResponse {
        logger.info("=========================== SkillDisabledRequest =========================")
        val inputHandler: SkillDisabledRequestHandler? = getHandler(SkillDisabledRequestHandler::class)?.cast()
        return inputHandler?.let {
            val alexaResponse = inputHandler.onSkillDisabledRequest(alexaRequest)
            return generateResponse(inputHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }

    override fun handleSkillEnabledRequest(alexaRequest: AlexaRequest<SkillEnabledRequest>): AlexaResponse {
        logger.info("=========================== SkillEnabledRequest =========================")
        val inputHandler: SkillEnabledRequestHandler? = getHandler(SkillEnabledRequestHandler::class)?.cast()
        return inputHandler?.let {
            val alexaResponse = inputHandler.onSkillEnabledRequest(alexaRequest)
            return generateResponse(inputHandler, alexaRequest, alexaResponse)
        } ?: IntentUtil.unsupportedIntent()
    }
}
