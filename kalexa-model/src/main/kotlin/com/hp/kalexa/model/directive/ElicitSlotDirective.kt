package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.Intent

@JsonTypeName("Dialog.ElicitSlot")
class ElicitSlotDirective(
        val updatedIntent: Intent? = null,
        val slotToElicit: String? = null) : Directive()