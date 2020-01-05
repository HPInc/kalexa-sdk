/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.persistentattributes

import com.hp.kalexa.core.handler.SkillConfig
import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.core.interceptor.RequestInterceptor
import com.hp.kalexa.core.interceptor.ResponseInterceptor

class PersistentAttributesSkillConfig(
    tableName: String,
    partitionKeyName: String? = null,
    attributesKeyName: String? = null,
    autoCreateTable: Boolean = false,
    intentHandlers: List<BaseHandler> = emptyList(),
    requestInterceptors: List<RequestInterceptor> = emptyList(),
    responseInterceptors: List<ResponseInterceptor> = emptyList()
) : SkillConfig(
    intentHandlers,
    requestInterceptors,
    responseInterceptors
) {
    init {
        if (tableName.isNotEmpty()) {
            DynamoDbPersistentAttributes.initialize(
                tableName,
                attributesKeyName,
                partitionKeyName,
                autoCreateTable
            )
        } else {
            throw IllegalArgumentException("Table name must be provided for creating Persistent Attributes Skill Config.")
        }
    }
}