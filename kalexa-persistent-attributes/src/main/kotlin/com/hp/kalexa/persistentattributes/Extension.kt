/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.persistentattributes

import com.hp.kalexa.core.intent.BaseHandler

/**
 * Persists all session attributes into database by a given id
 * @param id to save all session attributes
 * attributes. Default is true
 */
fun MutableMap<String, Any>.persist(id: String) {
    DynamoDbPersistentAttributes.persistentAttributes.saveAttributes(id, this)
}

/**
 * Loads all session attributes stored in database by a given id
 * @param id to retrieve all session attributes
 * @param shouldMerge flag that allows merging the loaded session attributes from database with the current session
 * attributes. Default is true
 */
fun MutableMap<String, Any>.load(id: String, shouldMerge: Boolean = true): Map<String, Any> {
    val map = DynamoDbPersistentAttributes.persistentAttributes.getAttributes(id) ?: emptyMap()
    if (shouldMerge) {
        putAll(map)
    }
    return this
}

fun BaseHandler.loadPersistentAttributes(id: String): Map<String, Any> {
    return DynamoDbPersistentAttributes.persistentAttributes.getAttributes(id) ?: emptyMap()
}

fun BaseHandler.savePersistentAttributes(id: String, map: Map<String, Any>) {
    DynamoDbPersistentAttributes.persistentAttributes.saveAttributes(id, map)
}
