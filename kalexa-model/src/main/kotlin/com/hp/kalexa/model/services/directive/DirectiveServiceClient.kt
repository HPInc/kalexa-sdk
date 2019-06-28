/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.directive

import com.hp.kalexa.model.directive.VoicePlayerSpeakDirective
import com.hp.kalexa.model.extension.toJson
import com.hp.kalexa.model.services.ApiClient
import com.hp.kalexa.model.services.ApiConfiguration
import com.hp.kalexa.model.services.BaseService
import com.hp.kalexa.model.services.ServiceException
import java.io.IOException

class DirectiveServiceClient(private val apiConfig: ApiConfiguration) : DirectiveService,
    BaseService(apiConfig.apiClient) {

    override fun progressiveResponse(requestId: String, speechText: String) {
        val uri = "${apiConfig.apiEndpoint}/v1/directives"
        try {
            val response = post(
                uri,
                getRequestHeaders(apiConfig.apiAccessToken),
                getDirective(requestId, speechText).toJson()
            )
            if (response.responseCode !in ApiClient.SUCCESS_CODE_RANGE) {
                throw ServiceException(response.responseBody)
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to delete list", e)
        }
    }

    private fun getDirective(requestId: String, speechText: String): ProgressiveResponse {
        return ProgressiveResponse(Header(requestId = requestId), VoicePlayerSpeakDirective(speech = speechText))
    }
}
