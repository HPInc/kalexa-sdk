/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.hp.kalexa.model.json.LocalDateTimeDeserializer
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.skillevents.AccountLinkedRequest
import com.hp.kalexa.model.skillevents.PermissionAcceptedRequest
import com.hp.kalexa.model.skillevents.PermissionChangedRequest
import com.hp.kalexa.model.skillevents.ProactiveSubscriptionChangedRequest
import com.hp.kalexa.model.skillevents.SkillDisabledRequest
import com.hp.kalexa.model.skillevents.SkillEnabledRequest
import java.time.LocalDateTime

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = ListCreatedEventRequest::class, name = "AlexaHouseholdListEvent.ListCreated"),
    JsonSubTypes.Type(value = ListUpdatedEventRequest::class, name = "AlexaHouseholdListEvent.ListUpdated"),
    JsonSubTypes.Type(value = ListDeletedEventRequest::class, name = "AlexaHouseholdListEvent.ListDeleted"),
    JsonSubTypes.Type(value = ListItemsCreatedEventRequest::class, name = "AlexaHouseholdListEvent.ItemsCreated"),
    JsonSubTypes.Type(value = ListItemsUpdatedEventRequest::class, name = "AlexaHouseholdListEvent.ItemsUpdated"),
    JsonSubTypes.Type(value = ListItemsDeletedEventRequest::class, name = "AlexaHouseholdListEvent.ItemsDeleted"),
    JsonSubTypes.Type(value = AccountLinkedRequest::class, name = "AlexaSkillEvent.SkillAccountLinked"),
    JsonSubTypes.Type(value = PermissionAcceptedRequest::class, name = "AlexaSkillEvent.SkillPermissionAccepted"),
    JsonSubTypes.Type(value = PermissionChangedRequest::class, name = "AlexaSkillEvent.SkillPermissionChanged"),
    JsonSubTypes.Type(
        value = ProactiveSubscriptionChangedRequest::class,
        name = "AlexaSkillEvent.ProactiveSubscriptionChanged"
    ),
    JsonSubTypes.Type(value = SkillDisabledRequest::class, name = "AlexaSkillEvent.SkillDisabled"),
    JsonSubTypes.Type(value = SkillEnabledRequest::class, name = "AlexaSkillEvent.SkillEnabled")
)
abstract class EventRequest(
    requestId: String,
    locale: String = "",
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    timestamp: LocalDateTime,
    originIpAddress: String? = null,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val eventCreationTime: LocalDateTime,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val eventPublishingTime: LocalDateTime
) : Request(requestId, locale, timestamp, originIpAddress)
