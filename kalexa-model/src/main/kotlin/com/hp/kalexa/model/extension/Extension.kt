/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.extension

import com.hp.kalexa.model.Session
import com.hp.kalexa.model.json.JacksonSerializer

inline fun <reified T> Map<String, Any?>.getAttr(key: String): T? = get(key) as? T
inline fun <reified T> Session.attribute(key: String): T? = attributes[key] as? T
fun Any.toJson(): String {
    return JacksonSerializer.serialize(this)
}
