/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.reminder

import com.hp.kalexa.model.request.event.reminder.GetReminderResponse
import com.hp.kalexa.model.request.event.reminder.GetRemindersResponse
import com.hp.kalexa.model.request.event.reminder.ReminderRequest
import com.hp.kalexa.model.request.event.reminder.ReminderResponse
import com.hp.kalexa.model.services.ServiceException

interface ReminderService {

    @Throws(ServiceException::class)
    fun deleteReminder(alertToken: String)

    @Throws(ServiceException::class)
    fun getReminder(alertToken: String): GetReminderResponse

    @Throws(ServiceException::class)
    fun updateReminder(alertToken: String, reminderRequest: ReminderRequest): ReminderResponse

    @Throws(ServiceException::class)
    fun createReminder(reminderRequest: ReminderRequest): ReminderResponse

    @Throws(ServiceException::class)
    fun getReminders(): GetRemindersResponse
}
