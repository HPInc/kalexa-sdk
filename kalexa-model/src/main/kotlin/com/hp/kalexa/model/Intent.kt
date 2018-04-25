package com.hp.kalexa.model


data class Intent(val name: String,
                  val slots: Map<String, Slot?> = emptyMap(),
                  val confirmationStatus: IntentConfirmationStatus) {

    fun getSlot(key: String) = slots[key]
}

