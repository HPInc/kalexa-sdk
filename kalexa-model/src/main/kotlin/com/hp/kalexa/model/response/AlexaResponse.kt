package com.hp.kalexa.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hp.kalexa.model.ConnectionsStatus
import com.hp.kalexa.model.directive.*
import com.hp.kalexa.model.interfaces.display.*
import com.hp.kalexa.model.interfaces.display.Image
import com.hp.kalexa.model.interfaces.video.Metadata
import com.hp.kalexa.model.interfaces.video.VideoItem
import com.hp.kalexa.model.payload.NameType
import com.hp.kalexa.model.payload.Payload
import com.hp.kalexa.model.payload.log.Log
import com.hp.kalexa.model.payload.log.PhysicalActivity
import com.hp.kalexa.model.payload.print.*
import com.hp.kalexa.model.ui.*
import java.time.LocalDateTime
import kotlin.reflect.full.primaryConstructor


fun alexaResponse(block: AlexaResponse.AlexaDSLResponseBuilder.() -> Unit): AlexaResponse = AlexaResponse.AlexaDSLResponseBuilder().apply(block).build()
fun textContent(block: (TextContent.() -> Unit)) = TextContent().apply(block)
fun plainText(block: (PlainText.() -> String)) = PlainText().apply { text = block() }
fun richText(block: (RichText.() -> String)) = RichText().apply { text = block() }


@DslMarker
annotation class AlexaResponseDsl

data class AlexaResponse(
        val response: Response = Response(),
        val sessionAttributes: Map<String, Any?> = emptyMap(),
        val version: String = "1.0") {

    companion object {
        private val OBJECT_MAPPER = jacksonObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)

        fun builder() = AlexaResponseBuilder()
        fun emptyResponse() = AlexaResponse()
    }

    fun toJsonByteArray(): ByteArray = OBJECT_MAPPER.writeValueAsBytes(this)
    fun toJson(): String = OBJECT_MAPPER.writeValueAsString(this)

    class AlexaResponseBuilder {
        private var sessionAttributes: Map<String, Any?> = emptyMap()
        private var version = "1.0"
        private var speech: OutputSpeech? = null
        private var card: Card? = null
        private val directiveList = mutableListOf<Directive>()
        private var shouldEndSession: Boolean? = true
        private var reprompt: Reprompt? = null

        fun build(): AlexaResponse {
            val speechletResponse = Response()
            speechletResponse.outputSpeech = speech
            speechletResponse.card = card
            speechletResponse.reprompt = reprompt
            speechletResponse.directives = directiveList
            speechletResponse.shouldEndSession = shouldEndSession
            return AlexaResponse(version = version, response = speechletResponse, sessionAttributes = sessionAttributes)
        }

        fun version(version: String): AlexaResponseBuilder {
            this.version = version
            return this
        }

        fun sessionAttributes(sessionAttributes: Map<String, Any?>): AlexaResponseBuilder {
            this.sessionAttributes = sessionAttributes
            return this
        }

        fun speech(speechText: String): AlexaResponseBuilder {
            val speech = PlainTextOutputSpeech()
            speech.text = speechText
            this.speech = speech
            return this
        }

        fun ssmlSpeech(speechText: String): AlexaResponseBuilder {
            val speech = SsmlOutputSpeech()
            speech.ssml = speechText
            this.speech = speech
            return this
        }

        fun consentCard(permissions: List<String>): AlexaResponseBuilder {
            val card = AskForPermissionsConsentCard(permissions)
            this.card = card
            return this
        }

        fun linkAccountCard(): AlexaResponseBuilder {
            card = LinkAccountCard()
            return this
        }

        fun simpleCard(cardTitle: String, cardText: String): AlexaResponseBuilder {
            val card = SimpleCard()
            card.content = cardText
            card.title = cardTitle
            this.card = card
            return this
        }

        fun standardCard(cardTitle: String, cardText: String, image: Image): AlexaResponseBuilder {
            val card = StandardCard()
            card.text = cardText
            card.image = image
            card.title = cardTitle
            this.card = card
            return this
        }

        fun reprompt(text: String): AlexaResponseBuilder {
            val reprompt = Reprompt()
            val speech = PlainTextOutputSpeech()
            speech.text = text
            reprompt.outputSpeech = speech
            this.reprompt = reprompt
            return this
        }

        fun shouldEndSession(shouldEndSession: Boolean?): AlexaResponseBuilder {
            this.shouldEndSession = shouldEndSession
            return this
        }

        fun addHintDirective(hintText: String): AlexaResponseBuilder {
            val hint = PlainTextHint()
            hint.text = hintText
            val hintDirective = HintDirective()
            hintDirective.hint = hint
            return addDirective(hintDirective)
        }

        fun addVideoDirective(videoURL: String, title: String, subTitle: String): AlexaResponseBuilder {
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

        fun addTemplateDirective(template: Template): AlexaResponseBuilder {
            val templateDirective = RenderTemplateDirective()
            templateDirective.template = template
            return addDirective(templateDirective)
        }

        fun addDirective(directive: Directive): AlexaResponseBuilder {
            directiveList.add(directive)
            return this
        }
    }

    @AlexaResponseDsl
    class AlexaDSLResponseBuilder {
        private var sessionAttributes: Map<String, Any?> = emptyMap()
        var version = "1.0"
        private var response: Response = Response()

        fun build(): AlexaResponse {
            return AlexaResponse(response, sessionAttributes, version)
        }

        fun sessionAttributes(block: () -> Map<String, Any?>) {
            sessionAttributes = block()
        }

        fun response(block: ResponseBuilder.() -> Unit) {
            response = ResponseBuilder().apply(block).build()
        }
    }

    @AlexaResponseDsl
    class ResponseBuilder {
        private var outputSpeech: OutputSpeech? = null
        private var card: Card? = null
        private val directives = mutableListOf<Directive>()
        var shouldEndSession: Boolean? = true
        private var reprompt: Reprompt? = null

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
            return speechletResponse

        }
    }

    @AlexaResponseDsl
    class Directives : ArrayList<Directive>() {
        private var metadata: Metadata? = null

        fun templateDirective(block: RenderTemplateDirective.() -> Unit) {
            add(RenderTemplateDirective().apply { block() })
        }

        fun metadata(block: Metadata.() -> Unit) {
            metadata = Metadata().apply { block() }
        }

        fun videoDirective(block: VideoItem.() -> Unit) {
            val videoItem = VideoItem().apply { block() }
            videoItem.metadata = videoItem.metadata ?: metadata
            val videoDirective = LaunchDirective()
            videoDirective.videoItem = videoItem
            add(videoDirective)
        }

        fun hintDirective(block: PlainTextHint.() -> Unit) {
            val hint = PlainTextHint().apply { block() }
            val hintDirective = HintDirective()
            hintDirective.hint = hint
            add(hintDirective)
        }

        fun sendRequestDirective(block: SendRequestDirectiveBuilder.() -> Unit) {
            add(SendRequestDirectiveBuilder().apply { block() }.build())
        }

        fun delegateDirective(block: (DelegateDirective.() -> Unit)) {
            add(DelegateDirective().apply { block() })
        }

        fun confirmIntentDirective(block: (ConfirmIntentDirective.() -> Unit)) {
            add(ConfirmIntentDirective().apply { block() })
        }

        fun confirmSlotDirective(block: (ConfirmSlotDirective.() -> Unit)) {
            add(ConfirmSlotDirective().apply { block() })
        }

        fun renderTemplateDirective(block: (RenderTemplateDirective.() -> Template)) {
            add(RenderTemplateDirective().apply { template = block() })
        }

        fun template(block: () -> Template): Template {
            return block()
        }

        fun listTemplate2(block: (ListTemplate2.() -> Unit)): ListTemplate2 {
            return ListTemplate2().apply { block() }
        }

        fun listTemplate1(block: (ListTemplate1.() -> Unit)): ListTemplate1 {
            return ListTemplate1().apply { block() }
        }

        fun bodyTemplate7(block: (BodyTemplate7.() -> Unit)): BodyTemplate7 {
            return BodyTemplate7().apply { block() }
        }

        fun bodyTemplate6(block: (BodyTemplate6.() -> Unit)): BodyTemplate6 {
            return BodyTemplate6().apply { block() }
        }

        fun bodyTemplate3(block: (BodyTemplate3.() -> Unit)): BodyTemplate3 {
            return BodyTemplate3().apply { block() }
        }

        fun bodyTemplate2(block: (BodyTemplate2.() -> Unit)): BodyTemplate2 {
            return BodyTemplate2().apply { block() }
        }

        fun bodyTemplate1(block: (BodyTemplate1.() -> Unit)): BodyTemplate1 {
            return BodyTemplate1().apply { block() }
        }

        fun directive(block: () -> Directive) {
            add(block())
        }

        @AlexaResponseDsl
        class SendRequestDirectiveBuilder {
            lateinit var name: NameType
            private lateinit var payload: Payload<*>
            var token: String = "none"

            fun webPage(block: PrintTypeBuilder.() -> Unit): Print<WebPage> {
                return Print(PrintTypeBuilder().apply { block() }.build())
            }

            fun pdf(block: PrintTypeBuilder.() -> Unit): Print<PDF> {
                return Print(PrintTypeBuilder().apply { block() }.build())
            }

            fun imagePNG(block: PrintTypeBuilder.() -> Unit): Print<ImagePNG> {
                return Print(PrintTypeBuilder().apply(block).build())
            }

            fun imageJPEG(block: PrintTypeBuilder.() -> Unit): Print<ImageJPEG> {
                return Print(PrintTypeBuilder().apply(block).build())
            }

            fun physicalActivity(block: PhysicalActivityBuilder.() -> Unit): Log<PhysicalActivity> {
                return Log(PhysicalActivityBuilder().apply(block).build())
            }

            fun print(block: PrintBuilder.() -> Unit): Print<*> = PrintBuilder().apply { block() }.build()

            fun log(block: LogBuilder.() -> Unit): Log<*> = LogBuilder().apply { block() }.build()

            fun payload(block: SendRequestDirectiveBuilder.() -> Payload<*>) {
                apply { payload = block() }
            }

            fun build(): SendRequestDirective = SendRequestDirective(name, payload, token)
        }


        @AlexaResponseDsl
        class ConnectionsStatusBuilder {
            var code: String = ""
            var message: String = ""
            fun build() = ConnectionsStatus(code, message)
        }

        @AlexaResponseDsl
        class PrintBuilder {
            private lateinit var printType: Print.PrintType
            fun webPage(block: PrintTypeBuilder.() -> Unit) {
                printType = PrintTypeBuilder().apply(block).build<WebPage>()
            }

            fun pdf(block: PrintTypeBuilder.() -> Unit) {
                printType = PrintTypeBuilder().apply(block).build<PDF>()
            }

            fun imagePNG(block: PrintTypeBuilder.() -> Unit) {
                printType = PrintTypeBuilder().apply(block).build<ImagePNG>()
            }

            fun imageJPEG(block: PrintTypeBuilder.() -> Unit) {
                printType = PrintTypeBuilder().apply(block).build<ImageJPEG>()
            }

            fun build(): Print<*> = Print(printType)
        }

        @AlexaResponseDsl
        class PrintTypeBuilder {
            lateinit var title: String
            lateinit var description: String
            lateinit var url: String

            inline fun <reified T : Print.PrintType> build(): T {
                return T::class.primaryConstructor!!.call(title, description, url)
            }
        }

        @AlexaResponseDsl
        class LogBuilder {
            lateinit var logType: Log.LogType

            fun physicalActivity(block: PhysicalActivityBuilder.() -> Unit) {
                logType = PhysicalActivityBuilder().apply(block).build()
            }

            fun build(): Log<*> = Log(logType)
        }

        @AlexaResponseDsl
        class PhysicalActivityBuilder {
            var description: String = ""
            lateinit var startTime: LocalDateTime
            var duration: Float = 0F
            var distance: Float = 0f
            fun build() = PhysicalActivity(description, startTime, duration, distance)
        }
    }
}

fun main(args: Array<String>) {
    val webPage = WebPage(title = "Wikipedia", description = "Wikipedia Article about Longest", url = "https://en.wikipedia.org/wiki/Main_Page/")
    val alexaResponse = alexaResponse {
        sessionAttributes { mapOf("Name" to "Marcelo") }
        version = "1.0"
        response {
            speech { "Marcelo" }
            simpleCard {
                title = "Marcelo"
                content = "HUM HUM HUMMM"
            }
            shouldEndSession = true
            directives {
                hintDirective {
                    text = "HELP"
                }
                videoDirective {
                    metadata {
                        title = "video"
                        subtitle = "subvideo"
                    }
                    source = "http://www.oi.com"
                }

                directive {
                    val hintDirective = HintDirective()
                    val hint = PlainTextHint()
                    hint.text = "TESTE"
                    hintDirective.hint = hint
                    hintDirective
                }
            }
        }
    }

    val list = ListItem()
    list.textContent {
        primaryText = plainText { "Marcelo" }
        secondaryText = plainText { "Rodrigues" }
        tertiaryText = richText { "Correa" }
    }
    println(list.textContent)
    println(alexaResponse.toJson())

    println(alexaResponse {
        response {
            shouldEndSession = false
            directives {
                sendRequestDirective {
                    name = NameType.PRINT
                    payload {
                        webPage {
                            title = ""
                            description = ""
                            url = "asdiouasdioas"
                        }
                    }
                }
            }
        }
    }.toJson())

    println(textContent {
        tertiaryText = plainText { "oi" }
    })
}