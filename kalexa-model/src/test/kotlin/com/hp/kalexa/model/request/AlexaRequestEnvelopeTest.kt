package com.hp.kalexa.model.request

import com.hp.kalexa.model.JsonRequests.DISPLAY_SELECTED_REQUEST
import com.hp.kalexa.model.JsonRequests.ERROR_LINK_RESULT
import com.hp.kalexa.model.JsonRequests.INTENT_REQUEST_JSON
import com.hp.kalexa.model.JsonRequests.LAUNCH_REQUEST_JSON
import com.hp.kalexa.model.JsonRequests.WEB_PAGE_LINK_RESULT
import com.hp.kalexa.model.extension.attribute
import com.hp.kalexa.model.payload.print.PrintWebPageRequest
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
                val action = envelope.session?.attribute<String>("action")
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
        on("a Connections.Response request") {
            val envelope = AlexaRequestEnvelope.fromJson(WEB_PAGE_LINK_RESULT)
            it("should be ConnectionsResponseRequest type") {
                assert(envelope.request is ConnectionsResponseRequest)
            }
            val customLinkResultRequest = envelope.request as ConnectionsResponseRequest
            it("should be a PrintWebPageRequest payload type") {
                assert(customLinkResultRequest.payload is Map<String, Any>)
            }
            it("should parse title, description and url") {
                val printWebPageRequest = customLinkResultRequest.payload
                assertEquals("Mac & Cheese", printWebPageRequest?.get("title"))
                assertEquals("This is a nice rich mac and cheese. Serve with a salad for a great meatless dinner. Hope you enjoy it", printWebPageRequest?.get("description"))
                assertEquals("http://allrecipes.com/recipe/11679/homemade-mac-and-cheese/", printWebPageRequest?.get("url"))
            }
            it("should have a success connectionsStatus") {
                assertEquals("200", customLinkResultRequest.status.code)
                assertEquals("OK", customLinkResultRequest.status.message)
            }
            it("should have Print Name") {
                assertEquals("Print", customLinkResultRequest.name)
            }
        }
        on("a error link result request") {
            val envelope = AlexaRequestEnvelope.fromJson(ERROR_LINK_RESULT)
            it("should be CustomLinkResultRequest type") {
                assert(envelope.request is ConnectionsResponseRequest)
            }
            val customLinkResultRequest = envelope.request as ConnectionsResponseRequest
            it("should have an empty payload type") {
                assertNull(customLinkResultRequest.payload)
            }

            it("should have an Error connectionsStatus") {
                assertEquals("500", customLinkResultRequest.status.code)
                assertEquals("INTERNAL ERROR", customLinkResultRequest.status.message)
            }
            it("should have target URI") {
                assertEquals("Print", customLinkResultRequest.name)
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