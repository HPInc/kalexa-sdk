package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.Directive
import com.hp.kalexa.model.Intent

@JsonTypeName("Dialog.ConfirmIntent")
data class ConfirmIntentDirective(val updatedIntent: Intent? = null) : Directive()

