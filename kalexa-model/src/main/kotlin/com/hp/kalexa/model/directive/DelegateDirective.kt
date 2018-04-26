package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.Intent

@JsonTypeName("Dialog.Delegate")
data class DelegateDirective(val updatedIntent: Intent? = null) : Directive()

