package com.hp.kalexa.model.response

import com.hp.kalexa.model.ConnectionsStatus
import com.hp.kalexa.model.directive.*
import com.hp.kalexa.model.interfaces.display.*
import com.hp.kalexa.model.interfaces.display.Image
import com.hp.kalexa.model.interfaces.video.VideoItem
import com.hp.kalexa.model.payload.NameType
import com.hp.kalexa.model.payload.Payload
import com.hp.kalexa.model.payload.print.PrintBuilder
import com.hp.kalexa.model.payload.print.PrintImageRequest
import com.hp.kalexa.model.payload.print.PrintPDFRequest
import com.hp.kalexa.model.payload.print.PrintWebPageRequest
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl
import com.hp.kalexa.model.ui.*

fun alexaResponse(block: AlexaDSLResponseBuilder.() -> Unit): AlexaResponse = AlexaDSLResponseBuilder().apply(block).build()
fun textContent(block: (TextContent.() -> Unit)) = TextContent().apply(block)
fun plainText(block: (PlainText.() -> String)) = PlainText().apply { text = block() }
fun richText(block: (RichText.() -> String)) = RichText().apply { text = block() }


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
    private var metadata: com.hp.kalexa.model.interfaces.video.Metadata? = null

    fun templateDirective(block: RenderTemplateDirective.() -> Unit) {
        add(RenderTemplateDirective().apply { block() })
    }

    fun metadata(block: com.hp.kalexa.model.interfaces.video.Metadata.() -> Unit) {
        metadata = com.hp.kalexa.model.interfaces.video.Metadata().apply { block() }
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

    fun sendResponseDirective(block: SendResponseDirectiveBuilder.() -> Unit) {
        add(SendResponseDirectiveBuilder().apply { block() }.build())
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

    fun listTemplate2(block: (ListTemplateBuilder.() -> Unit)): ListTemplate2 {
        return ListTemplateBuilder().apply { block() }.buildListTemplate2()
    }

    fun listTemplate1(block: (ListTemplateBuilder.() -> Unit)): ListTemplate1 {
        return ListTemplateBuilder().apply { block() }.buildListTemplate1()
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
    class ListTemplateBuilder {
        var backgroundImage: Image? = null
        var title: String = ""
        var listItems = mutableListOf<ListItem>()

        fun listItems(block: ListItemsBuilder.() -> Unit) {
            listItems.addAll(ListItemsBuilder().apply { block() })
        }

        fun buildListTemplate1() = ListTemplate1(backgroundImage, title, listItems)

        fun buildListTemplate2() = ListTemplate2(backgroundImage, title, listItems)
    }

    @AlexaResponseDsl
    class ListItemsBuilder : ArrayList<ListItem>() {
        fun listItem(block: ListItem.() -> Unit) {
            add(ListItem().apply { block() })
        }
    }

    @AlexaResponseDsl
    class SendRequestDirectiveBuilder {
        lateinit var name: NameType
        private lateinit var payload: Payload
        var token: String = "none"

        fun printPDFRequest(block: PrintPDFRequestBuilder.() -> Unit) {
            apply { payload = PrintPDFRequestBuilder().apply { block() }.build() }
        }

        fun printWebPageRequest(block: PrintWebPageRequestBuilder.() -> Unit) {
            apply { payload = PrintWebPageRequestBuilder().apply { block() }.build() }
        }

        fun printImageRequest(block: PrintImageRequestBuilder.() -> Unit) {
            apply { payload = PrintImageRequestBuilder().apply { block() }.build() }
        }

        fun payload(block: SendRequestDirectiveBuilder.() -> Payload) {
            apply { payload = block() }
        }

        fun build(): SendRequestDirective = SendRequestDirective(name, payload, token)
    }

    @AlexaResponseDsl
    class PrintPDFRequestBuilder : PrintBuilder<PrintPDFRequest>() {

        override fun build() = PrintPDFRequest(version, context, title, description, url)

        companion object {
            @JvmStatic
            fun builder() = PrintPDFRequestBuilder()
        }
    }

    @AlexaResponseDsl
    class PrintWebPageRequestBuilder : PrintBuilder<PrintWebPageRequest>() {
        override fun build() = PrintWebPageRequest(version, context, title, description, url)

        companion object {
            @JvmStatic
            fun builder() = PrintWebPageRequestBuilder()
        }
    }

    @AlexaResponseDsl
    class PrintImageRequestBuilder : PrintBuilder<PrintImageRequest>() {

        private lateinit var imageType: PrintImageRequest.ImageType
        fun imageType(block: () -> PrintImageRequest.ImageType) = apply {
            imageType = block()
        }

        override fun build() = PrintImageRequest(version, context, title, description, imageType, url)

        companion object {
            @JvmStatic
            fun builder() = PrintImageRequestBuilder()
        }
    }

    @AlexaResponseDsl
    class SendResponseDirectiveBuilder {
        lateinit var status: ConnectionsStatus
        lateinit var payload: Map<String, Any>

        fun status(block: () -> ConnectionsStatus) {
            apply { status = block() }
        }

        fun payload(block: SendResponseDirectiveBuilder.() -> Map<String, Any>) {
            apply { payload = block() }
        }

        fun build(): SendResponseDirective = SendResponseDirective(status, payload)
    }


    @AlexaResponseDsl
    class ConnectionsStatusBuilder {
        var code: String = ""
        var message: String = ""
        fun build() = ConnectionsStatus(code, message)
    }
}