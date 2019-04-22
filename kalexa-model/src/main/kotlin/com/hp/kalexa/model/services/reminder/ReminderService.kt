/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.reminder

import com.hp.kalexa.model.request.event.reminder.GetReminderResponse
import com.hp.kalexa.model.request.event.reminder.GetRemindersResponse
import com.hp.kalexa.model.request.event.reminder.ReminderRequest
import com.hp.kalexa.model.request.event.reminder.ReminderResponse
import com.hp.kalexa.model.services.BaseService
import com.hp.kalexa.model.services.ServiceException

interface ReminderService : BaseService {

    @Throws(ServiceException::class)
    fun deleteReminder(alertToken: String, accessToken: String)

    @Throws(ServiceException::class)
    fun getReminder(alertToken: String, accessToken: String): GetReminderResponse

    @Throws(ServiceException::class)
    fun updateReminder(alertToken: String, accessToken: String, reminderRequest: ReminderRequest): ReminderResponse

    @Throws(ServiceException::class)
    fun createReminder(accessToken: String, reminderRequest: ReminderRequest): ReminderResponse

    @Throws(ServiceException::class)
    fun getReminders(accessToken: String): GetRemindersResponse
}
