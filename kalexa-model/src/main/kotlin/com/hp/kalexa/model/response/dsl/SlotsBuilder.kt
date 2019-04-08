package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.canfulfill.CanFulfillSlot
import com.hp.kalexa.model.canfulfill.CanFulfillSlotValues
import com.hp.kalexa.model.canfulfill.CanUnderstandSlotValues
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class SlotsBuilder : HashMap<String, CanFulfillSlot>() {
    fun slot(block: SlotBuilder.() -> Unit) {
        val slotbuilder = SlotBuilder().apply { block() }
        put(slotbuilder.name, CanFulfillSlot(slotbuilder.canUnderstand, slotbuilder.canFulfill))
    }
}

@AlexaResponseDsl
class SlotBuilder {
    var name: String = ""
    var canFulfill: CanFulfillSlotValues? = null
    var canUnderstand: CanUnderstandSlotValues? = null
}
