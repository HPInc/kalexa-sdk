package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.Intent

@JsonTypeName("Dialog.ConfirmSlot")
data class ConfirmSlotDirective(
        val updatedIntent: Intent? = null,
        val slotToConfirm: String? = null) : Directive()

