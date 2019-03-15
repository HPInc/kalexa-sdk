/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hp.kalexa.model.directive.Directive
import com.hp.kalexa.model.directive.HintDirective
import com.hp.kalexa.model.directive.LaunchDirective
import com.hp.kalexa.model.directive.RenderTemplateDirective
import com.hp.kalexa.model.interfaces.display.PlainTextHint
import com.hp.kalexa.model.interfaces.display.Template
import com.hp.kalexa.model.interfaces.video.Metadata
import com.hp.kalexa.model.interfaces.video.VideoItem
import com.hp.kalexa.model.ui.AskForPermissionsConsentCard
import com.hp.kalexa.model.ui.Card
import com.hp.kalexa.model.ui.LinkAccountCard
import com.hp.kalexa.model.ui.OutputSpeech
import com.hp.kalexa.model.ui.PlainTextOutputSpeech
import com.hp.kalexa.model.ui.Reprompt
import com.hp.kalexa.model.ui.SimpleCard
import com.hp.kalexa.model.ui.SsmlOutputSpeech
import com.hp.kalexa.model.ui.StandardCard

data class AlexaResponse(
    val response: Response = Response(),
    val sessionAttributes: Map<String, Any?> = emptyMap(),
    val version: String = "1.0"
) {

    companion object {
        private val OBJECT_MAPPER = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

        @JvmStatic
        fun builder() = Builder()

        fun emptyResponse() = AlexaResponse()
    }

    fun toJsonByteArray(): ByteArray = OBJECT_MAPPER.writeValueAsBytes(this)
    fun toJson(): String = OBJECT_MAPPER.writeValueAsString(this)

    class Builder {
        private var sessionAttributes: Map<String, Any?> = emptyMap()
        private var version = "1.0"
        private var speech: OutputSpeech? = null
        private var card: Card? = null
        private val directiveList = mutableListOf<Directive>()
        private var shouldEndSession: Boolean? = true
        private var reprompt: Reprompt? = null

        fun build(): AlexaResponse {
            val response = Response()
            response.outputSpeech = speech
            response.card = card
            response.reprompt = reprompt
            response.directives = directiveList
            response.shouldEndSession = shouldEndSession
            return AlexaResponse(version = version, response = response, sessionAttributes = sessionAttributes)
        }

        fun version(version: String): Builder {
            this.version = version
            return this
        }

        fun sessionAttributes(sessionAttributes: Map<String, Any?>): Builder {
            this.sessionAttributes = sessionAttributes
            return this
        }

        fun speech(speechText: String): Builder {
            val speech = PlainTextOutputSpeech()
            speech.text = speechText
            this.speech = speech
            return this
        }

        fun ssmlSpeech(speechText: String): Builder {
            val speech = SsmlOutputSpeech()
            speech.ssml = speechText
            this.speech = speech
            return this
        }

        fun consentCard(permissions: List<String>): Builder {
            val card = AskForPermissionsConsentCard(permissions)
            this.card = card
            return this
        }

        fun linkAccountCard(): Builder {
            card = LinkAccountCard()
            return this
        }

        fun simpleCard(cardTitle: String, cardText: String): Builder {
            val card = SimpleCard()
            card.content = cardText
            card.title = cardTitle
            this.card = card
            return this
        }

        fun standardCard(cardTitle: String, cardText: String, image: com.hp.kalexa.model.ui.Image): Builder {
            val card = StandardCard()
            card.text = cardText
            card.image = image
            card.title = cardTitle
            this.card = card
            return this
        }

        fun reprompt(text: String): Builder {
            val reprompt = Reprompt()
            val speech = PlainTextOutputSpeech()
            speech.text = text
            reprompt.outputSpeech = speech
            this.reprompt = reprompt
            return this
        }

        fun shouldEndSession(shouldEndSession: Boolean?): Builder {
            this.shouldEndSession = shouldEndSession
            return this
        }

        fun addHintDirective(hintText: String): Builder {
            val hint = PlainTextHint()
            hint.text = hintText
            val hintDirective = HintDirective()
            hintDirective.hint = hint
            return addDirective(hintDirective)
        }

        fun addVideoDirective(videoURL: String, title: String, subTitle: String): Builder {
            val metadata = Metadata()
            metadata.subtitle = subTitle
            metadata.title = title

            val videoItem = VideoItem()
            videoItem.source = videoURL
            videoItem.metadata = metadata
            val videoDirective = LaunchDirective()
            videoDirective.videoItem = videoItem

            return addDirective(videoDirective)
        }

        fun addTemplateDirective(template: Template): Builder {
            val templateDirective = RenderTemplateDirective()
            templateDirective.template = template
            return addDirective(templateDirective)
        }

        fun addDirective(directive: Directive): Builder {
            directiveList.add(directive)
            return this
        }
    }
}