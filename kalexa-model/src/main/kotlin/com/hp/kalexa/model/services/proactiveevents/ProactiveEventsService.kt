/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents

import com.hp.kalexa.model.services.ServiceException

interface ProactiveEventsService {
    @Throws(ServiceException::class)
    fun createProactiveEvent(createProactiveEventRequest: CreateProactiveEventRequest, stage: SkillStage)
}
