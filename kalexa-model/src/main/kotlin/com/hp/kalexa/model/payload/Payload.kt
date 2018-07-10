package com.hp.kalexa.model.payload

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hp.kalexa.model.directive.SendRequestDirective
import com.hp.kalexa.model.payload.print.Language
import com.hp.kalexa.model.payload.print.PrintImageRequest
import com.hp.kalexa.model.payload.print.PrintPDFRequest
import com.hp.kalexa.model.payload.print.PrintWebPageRequest

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
@JsonSubTypes(
        JsonSubTypes.Type(value = PrintWebPageRequest::class, name = "PrintWebPageRequest"),
        JsonSubTypes.Type(value = PrintPDFRequest::class, name = "PrintPDFRequest"),
        JsonSubTypes.Type(value = PrintImageRequest::class, name = "PrintImageRequest"))
abstract class Payload(
        @field:JsonProperty("@version")
        val version: String,
        @field:JsonProperty("@language")
        val language: Language) {

    companion object {
        inline fun <reified T : Payload> newRequest(block: T.() -> Unit): Payload {
            return T::class.java.newInstance().apply(block)
        }
    }
//
//        inline fun <reified T : RequestType> newRequest(obj: T): Payload {
//            return when (T::class) {
//                PDF::class -> PrintPDFRequest(obj as PDF)
////                WebPage::class -> PrintWebPageRequest(obj as WebPage)
//                ImagePNG::class -> PrintImagePNGRequest(obj as ImagePNG)
//                ImageJPEG::class -> PrintImageJPEGRequest(obj as ImageJPEG)
//                PhysicalActivity::class -> LogPhysicalActivityRequest(obj as PhysicalActivity)
//                else -> throw IllegalArgumentException("Request type not supported")
//            }
//        }
//    }

}

fun main(args: Array<String>) {
    val imageRequest = PrintImageRequest(
            title = "Title",
            imageType = PrintImageRequest.ImageType.JPEG,
            url = "http://www.oi.com"
    )
    Payload.newRequest<PrintPDFRequest> {
        description = ""
    }
    println(jacksonObjectMapper().writeValueAsString(imageRequest))
    println(Payload.newRequest<PrintImageRequest> {
        title = "Image PNG"
        description = "Random png image by Longest."
        url = "https://s3.amazonaws.com/uploads.hipchat.com/716626/5042546/blJjSQ4oi8pvOsp/fulfiller.png"
        imageType = PrintImageRequest.ImageType.PNG
    })

    val followLinkWithResultDirective = SendRequestDirective(
            name = NameType.PRINT,
            payload = Payload.newRequest<PrintImageRequest> {
                title = "Image PNG"
                description = "Random png image by Longest."
                url = "https://s3.amazonaws.com/uploads.hipchat.com/716626/5042546/blJjSQ4oi8pvOsp/fulfiller.png"
                imageType = PrintImageRequest.ImageType.PNG
            },
            token = "PrintPNG")
    println(followLinkWithResultDirective)
}