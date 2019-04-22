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
import com.hp.kalexa.model.services.ApiClient
import com.hp.kalexa.model.services.BaseService.Companion.API_ENDPOINT
import com.hp.kalexa.model.services.ServiceException
import com.hp.kalexa.model.services.toTypedObject
import java.io.IOException

class ReminderServiceClient(private val client: ApiClient = ApiClient()) : ReminderService {

    override fun deleteReminder(alertToken: String, accessToken: String) {
        val uri = "$API_ENDPOINT/v1/alerts/reminders/$alertToken"
        client.delete(uri, getRequestHeaders(accessToken))
    }

    override fun getReminders(accessToken: String): GetRemindersResponse {
        val uri = "$API_ENDPOINT/v1/alerts/reminders/"
        try {
            val response = client.get(uri, getRequestHeaders(accessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to get reminders", e)
        }
    }

    override fun getReminder(alertToken: String, accessToken: String): GetReminderResponse {
        val uri = "$API_ENDPOINT/v1/alerts/reminders/$alertToken"
        try {
            val response = client.get(uri, getRequestHeaders(accessToken))
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to get reminder", e)
        }
    }

    override fun updateReminder(
        alertToken: String,
        accessToken: String,
        reminderRequest: ReminderRequest
    ): ReminderResponse {
        val uri = "$API_ENDPOINT/v1/alerts/reminders/$alertToken"
        try {
            val response = client.put(uri, getRequestHeaders(accessToken), reminderRequest.toJson())
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to update reminder", e)
        }
    }

    override fun createReminder(accessToken: String, reminderRequest: ReminderRequest): ReminderResponse {
        val uri = "$API_ENDPOINT/v1/alerts/reminders/"
        try {
            val response = client.post(uri, getRequestHeaders(accessToken), reminderRequest.toJson())
            return response.toTypedObject()
        } catch (e: IOException) {
            throw ServiceException("Encountered an IOException while attempting to create reminder", e)
        }
    }
}