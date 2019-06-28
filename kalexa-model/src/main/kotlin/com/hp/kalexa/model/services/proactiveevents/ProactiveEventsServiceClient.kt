/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents

import com.hp.kalexa.model.json.JacksonSerializer
import com.hp.kalexa.model.services.ApiConfiguration
import com.hp.kalexa.model.services.AuthenticationConfiguration
import com.hp.kalexa.model.services.BaseService
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.lwa.LwaClient
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException
import java.net.HttpURLConnection.HTTP_ACCEPTED
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_CONFLICT
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR

class ProactiveEventsServiceClient(
    private val apiConfiguration: ApiConfiguration,
    authenticationConfiguration: AuthenticationConfiguration
) : ProactiveEventsService, BaseService(apiConfiguration.apiClient) {

    private val lwaClient: LwaClient = LwaClient(apiConfiguration, authenticationConfiguration)

    @Throws(ServiceException::class)
    override fun createProactiveEvent(createProactiveEventRequest: CreateProactiveEventRequest, stage: SkillStage) {
        val accessToken = lwaClient.getAccessTokenForScope("alexa::proactive_events")

        val uri = "${apiConfiguration.apiEndpoint}/v1/proactiveEvents"
        val path = if (stage == SkillStage.DEVELOPMENT) "$uri/stages/development" else uri
        try {
            val response = post(
                path,
                getRequestHeaders(accessToken),
                JacksonSerializer.serialize(createProactiveEventRequest)
            )
            when (response.responseCode) {
                HTTP_ACCEPTED -> return response.toTypedObject()
                HTTP_BAD_REQUEST -> throw ServiceException(
                    "A required parameter is not present or is incorrectly formatted, or" +
                        " the requested creation of a resource has already been completed by a previous request."
                )
                HTTP_FORBIDDEN -> throw ServiceException(
                    "The authentication token is invalid or doesn't have authentication" +
                        " to access the resource"
                )
                HTTP_CONFLICT -> throw ServiceException(
                    "A skill attempts to create duplicate events using the same referenceId " +
                        "for the same customer."
                )
                TOO_MANY_REQUESTS -> throw ServiceException("The client has made more calls than the allowed limit.")
                HTTP_INTERNAL_ERROR -> throw ServiceException(
                    "The ProactiveEvents service encounters an internal error for a valid " +
                        "request."
                )
                else -> throw ServiceException("Unexpected error")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to create a Proactive event.", e)
        }
    }

    companion object {
        const val TOO_MANY_REQUESTS = 429
    }
}
