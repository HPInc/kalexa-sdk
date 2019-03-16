/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

data class ConnectionsStatus(val code: String, val message: String) {

    fun isSuccess() = code == "200"
}
