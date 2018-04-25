package com.hp.kalexa.model.request

import com.hp.kalexa.model.ElementSelectedRequest
import com.hp.kalexa.model.IntentRequest
import com.hp.kalexa.model.LaunchRequest
import com.hp.kalexa.model.LinkResultRequest
import com.hp.kalexa.model.extension.attribute
import com.hp.kalexa.model.payload.EmptyPayload
import com.hp.kalexa.model.payload.print.Print
import com.hp.kalexa.model.payload.print.WebPage
import com.hp.kalexa.model.JsonRequests.DISPLAY_SELECTED_REQUEST
import com.hp.kalexa.model.JsonRequests.ERROR_LINK_RESULT
import com.hp.kalexa.model.JsonRequests.INTENT_REQUEST_JSON
import com.hp.kalexa.model.JsonRequests.LAUNCH_REQUEST_JSON
import com.hp.kalexa.model.JsonRequests.WEB_PAGE_LINK_RESULT
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

class AlexaRequestEnvelopeTest : Spek({

    given("a incoming json request") {

        on("a Launch Request") {
            val launchIntentRequest = AlexaRequestEnvelope.fromJson(LAUNCH_REQUEST_JSON)
            it("should be CustomLaunchRequest type") {
                assert(launchIntentRequest.request is LaunchRequest)
            }
        }

        on("a Intent request") {
            val envelope = AlexaRequestEnvelope.fromJson(INTENT_REQUEST_JSON)
            it("should be CustomIntentRequest type") {
                assert(envelope.request is IntentRequest)
            }
            it("should have MailingLabelIntent action in session attributes") {
                val action = envelope.session.attribute<String>("action")
                assertEquals("MailingLabelIntent", action)
            }
            val customIntentRequest = envelope.request as IntentRequest
            it("should have a jersey value as product slot") {
                assertEquals(1, customIntentRequest.intent.slots.size)
                val slot = customIntentRequest.intent.getSlot("product")
                assertEquals("jersey", slot?.value)
            }
            it("should match Internacional Jersey") {
                val slot = customIntentRequest.intent.getSlot("product")
                val jersey = slot?.resolutions?.resolutionsPerAuthority?.first()?.values?.first()?.value
                assertEquals("Internacional Jersey", jersey?.name)
            }
        }
        on("a Link Result request") {
            val envelope = AlexaRequestEnvelope.fromJson(WEB_PAGE_LINK_RESULT)
            it("should be CustomLinkResultRequest type") {
                assert(envelope.request is LinkResultRequest)
            }
            val customLinkResultRequest = envelope.request as LinkResultRequest
            it("should have a webpage payload type") {
                assert(customLinkResultRequest.payload is Print<*>)
                println(customLinkResultRequest.payload)
                assert(customLinkResultRequest.payload?.getType() is WebPage)
            }
            it("should parse title, description and url") {
                val webPage = customLinkResultRequest.payload?.getType() as WebPage
                assertEquals("Mac & Cheese", webPage.title)
                assertEquals("This is a nice rich mac and cheese. Serve with a salad for a great meatless dinner. Hope you enjoy it", webPage.description)
                assertEquals("http://allrecipes.com/recipe/11679/homemade-mac-and-cheese/", webPage.url)
            }
            it("should have a success status") {
                assertEquals("SUCCESS", customLinkResultRequest.status)
                assertEquals("200", customLinkResultRequest.linkStatus?.code)
                assertEquals("OK", customLinkResultRequest.linkStatus?.message)
            }
            it("should have target URI") {
                assertEquals("conn://amzn1.ask.skill.5a8a2654-2e1e-444b-98b3-6a4a617ef9b0/Print", customLinkResultRequest.targetURI)
            }
        }
        on("a error link result request") {
            val envelope = AlexaRequestEnvelope.fromJson(ERROR_LINK_RESULT)
            it("should be CustomLinkResultRequest type") {
                assert(envelope.request is LinkResultRequest)
            }
            val customLinkResultRequest = envelope.request as LinkResultRequest
            it("should have an empty payload type") {
                assert(customLinkResultRequest.payload is Print)
                assert(customLinkResultRequest.payload?.getType() is EmptyPayload)
            }
            it("should have an Error status") {
                assertEquals("INTERNAL_ERROR", customLinkResultRequest.status)
                assertEquals("500", customLinkResultRequest.linkStatus?.code)
                assertEquals("INTERNAL ERROR", customLinkResultRequest.linkStatus?.message)
            }
            it("should have target URI") {
                assertEquals("conn://amzn1.ask.skill.5a8a2654-2e1e-444b-98b3-6a4a617ef9b0/Print", customLinkResultRequest.targetURI)
            }
        }
        on("a display element element request") {
            val envelope = AlexaRequestEnvelope.fromJson(DISPLAY_SELECTED_REQUEST)
            it("should be CustomElementSelectedRequest type") {
                assert(envelope.request is ElementSelectedRequest)
            }
            val customElementSelectedRequest = envelope.request as ElementSelectedRequest
            it("should have a token attribute in the request") {
                assertEquals("TeamsIntent\\|Internacional", customElementSelectedRequest.token)
            }
        }
    }
})