/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.lwa

import com.hp.kalexa.model.services.ApiConfiguration
import com.hp.kalexa.model.services.AuthenticationConfiguration
import com.hp.kalexa.model.services.BaseService
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.lwa.model.AccessToken
import com.hp.kalexa.model.services.lwa.model.AccessTokenRequest
import com.hp.kalexa.model.services.lwa.model.AccessTokenResponse
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class LwaClient(
    private val apiConfig: ApiConfiguration,
    private val authenticationConfiguration: AuthenticationConfiguration
) : BaseService(apiConfig.apiClient) {
    private val scopeTokenStore: MutableMap<String, AccessToken> = ConcurrentHashMap()

    fun getAccessTokenForScope(scope: String?): String {
        if (scope == null || scope.isEmpty()) {
            throw IllegalArgumentException("Scope must be provided")
        }
        val token = scopeTokenStore[scope]
        return if (token != null && token.expiry > System.currentTimeMillis() + EXPIRATION_TIME) {
            token.token
        } else {
            val currentEpochMillis = System.currentTimeMillis()
            val lwaResponse = this.generateAccessToken(
                AccessTokenRequest(
                    clientId = authenticationConfiguration.clientId,
                    clientSecret = authenticationConfiguration.clientSecret,
                    scope = scope
                )
            )
            this.scopeTokenStore[scope] = AccessToken(
                token = lwaResponse.accessToken,
                expiry = currentEpochMillis + TimeUnit.SECONDS.toMillis(lwaResponse.expiresIn)
            )
            lwaResponse.accessToken
        }
    }

    private fun generateAccessToken(request: AccessTokenRequest): AccessTokenResponse {
        val uri = "${apiConfig.apiEndpoint}/auth/o2/token"
        val payload =
            "grant_type=client_credentials&client_id=${request.clientId}&client_secret=${request.clientSecret}&scope=${request.scope}"
        try {
            val response = post(uri, getRequestHeaders(), payload)
            when (response.responseCode) {
                HttpURLConnection.HTTP_OK -> return response.toTypedObject()
                HttpURLConnection.HTTP_BAD_REQUEST -> throw ServiceException("Bad Request")
                HttpURLConnection.HTTP_UNAUTHORIZED -> throw ServiceException("Authentication Failed")
                HttpURLConnection.HTTP_INTERNAL_ERROR -> throw ServiceException("Internal Server Error")
                else -> throw ServiceException("Unexpected error")
            }
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to generate access token", e)
        }
    }

    private fun getRequestHeaders(): Map<String, String> {
        return mapOf(
            "Content-Type" to "application/x-www-form-urlencoded"
        )
    }

    companion object {
        const val EXPIRATION_TIME = 60000L
    }
}
