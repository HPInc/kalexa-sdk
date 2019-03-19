/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response

import com.hp.kalexa.model.canfulfill.CanFulfillIntent
import com.hp.kalexa.model.directive.Directive
import com.hp.kalexa.model.ui.Card
import com.hp.kalexa.model.ui.OutputSpeech
import com.hp.kalexa.model.ui.Reprompt

data class Response(
    var outputSpeech: OutputSpeech? = null,
    var card: Card? = null,
    var reprompt: Reprompt? = null,
    var directives: List<Directive>? = emptyList(),
    var shouldEndSession: Boolean? = null,
    var canFulfillIntent: CanFulfillIntent? = null
)
