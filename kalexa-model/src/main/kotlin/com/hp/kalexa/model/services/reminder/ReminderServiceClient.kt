/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.reminder

import com.hp.kalexa.model.extension.toJson
import com.hp.kalexa.model.request.event.reminder.GetReminderResponse
import com.hp.kalexa.model.request.event.reminder.GetRemindersResponse
import com.hp.kalexa.model.request.event.reminder.ReminderRequest
import com.hp.kalexa.model.request.event.reminder.ReminderResponse
import com.hp.kalexa.model.services.ApiConfiguration
import com.hp.kalexa.model.services.BaseService
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException

class ReminderServiceClient(private val apiConfig: ApiConfiguration) : ReminderService,
    BaseService(apiConfig.apiClient) {

    override fun deleteReminder(alertToken: String) {
        val uri = "${apiConfig.apiEndpoint}/v1/alerts/reminders/$alertToken"
        delete(uri, getRequestHeaders(apiConfig.apiAccessToken))
    }

    override fun getReminders(): GetRemindersResponse {
        val uri = "${apiConfig.apiEndpoint}/v1/alerts/reminders/"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to get reminders", e)
        }
    }

    override fun getReminder(alertToken: String): GetReminderResponse {
        val uri = "${apiConfig.apiEndpoint}/v1/alerts/reminders/$alertToken"
        try {
            val response = get(uri, getRequestHeaders(apiConfig.apiAccessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to get reminder", e)
        }
    }

    override fun updateReminder(
        alertToken: String,
        reminderRequest: ReminderRequest
    ): ReminderResponse {
        val uri = "${apiConfig.apiEndpoint}/v1/alerts/reminders/$alertToken"
        try {
            val response = put(uri, getRequestHeaders(apiConfig.apiAccessToken), reminderRequest.toJson())
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to update reminder", e)
        }
    }

    override fun createReminder(reminderRequest: ReminderRequest): ReminderResponse {
        val uri = "${apiConfig.apiEndpoint}/v1/alerts/reminders/"
        try {
            val response = post(uri, getRequestHeaders(apiConfig.apiAccessToken), reminderRequest.toJson())
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to create reminder", e)
        }
    }
}
