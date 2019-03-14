/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model


data class Intent(val name: String,
                  val slots: Map<String, Slot?> = emptyMap(),
                  val confirmationStatus: IntentConfirmationStatus) {

    fun getSlot(key: String) = slots[key]
}