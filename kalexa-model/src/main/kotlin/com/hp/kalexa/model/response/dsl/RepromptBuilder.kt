/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.ui.OutputSpeech
import com.hp.kalexa.model.ui.PlainTextOutputSpeech
import com.hp.kalexa.model.ui.Reprompt
import com.hp.kalexa.model.ui.SsmlOutputSpeech

class RepromptBuilder {

    lateinit var outputSpeech: OutputSpeech

    fun speech(block: PlainTextOutputSpeech.() -> String) {
        outputSpeech = PlainTextOutputSpeech().apply { text = block() }
    }

    fun ssmlSpeech(block: SsmlOutputSpeech.() -> String) {
        outputSpeech = SsmlOutputSpeech().apply { ssml = block() }
    }

    fun build() = Reprompt(outputSpeech)
}
