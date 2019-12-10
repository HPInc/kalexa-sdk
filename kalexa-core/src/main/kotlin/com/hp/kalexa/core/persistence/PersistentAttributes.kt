/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.persistence

import com.hp.kalexa.core.persistence.exception.PersistentAttributesException

interface PersistentAttributes {
    @Throws(PersistentAttributesException::class)
    fun getAttributes(partitionKey: String): Map<String, Any>?

    @Throws(PersistentAttributesException::class)
    fun saveAttributes(partitionKey: String, attributes: Map<String, Any>)
}
