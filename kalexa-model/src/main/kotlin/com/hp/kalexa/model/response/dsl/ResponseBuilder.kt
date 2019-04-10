/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.canfulfill.CanFulfillIntent
import com.hp.kalexa.model.directive.Directive
import com.hp.kalexa.model.response.Response
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl
import com.hp.kalexa.model.ui.Card
import com.hp.kalexa.model.ui.OutputSpeech
import com.hp.kalexa.model.ui.PlainTextOutputSpeech
import com.hp.kalexa.model.ui.Reprompt
import com.hp.kalexa.model.ui.SimpleCard
import com.hp.kalexa.model.ui.SsmlOutputSpeech
import com.hp.kalexa.model.ui.StandardCard

@AlexaResponseDsl
class ResponseBuilder {
    private var outputSpeech: OutputSpeech? = null
    private var card: Card? = null
    private val directives = mutableListOf<Directive>()
    var shouldEndSession: Boolean? = true
    private var reprompt: Reprompt? = null
    var canFulfillIntent: CanFulfillIntent? = null

    fun speech(block: PlainTextOutputSpeech.() -> String) {
        outputSpeech = PlainTextOutputSpeech().apply { text = block() }
    }

    fun ssmlSpeech(block: SsmlOutputSpeech.() -> String) {
        outputSpeech = SsmlOutputSpeech().apply { ssml = block() }
    }

    fun card(block: () -> Card) {
        card = block()
    }

    fun simpleCard(block: SimpleCard.() -> Unit) {
        card = SimpleCard().apply { block() }
    }

    fun standardCard(block: StandardCard.() -> Unit) {
        card = StandardCard().apply { block() }
    }

    fun reprompt(block: Reprompt.() -> OutputSpeech) {
        reprompt = Reprompt().apply { outputSpeech = block() }
    }

    fun canFulfillIntent(block: CanFulfillIntentBuilder.() -> Unit) {
        canFulfillIntent = CanFulfillIntentBuilder().apply { block() }.build()
    }

    fun directives(block: Directives.() -> Unit) {
        directives.addAll(Directives().apply { block() })
    }

    fun build(): Response {
        val speechletResponse = Response()
        speechletResponse.outputSpeech = outputSpeech
        speechletResponse.card = card
        speechletResponse.reprompt = reprompt
        speechletResponse.directives = directives
        speechletResponse.shouldEndSession = shouldEndSession
        speechletResponse.canFulfillIntent = canFulfillIntent
        return speechletResponse
    }
}
