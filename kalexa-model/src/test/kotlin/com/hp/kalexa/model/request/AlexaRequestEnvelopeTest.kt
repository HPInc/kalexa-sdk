/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

import com.hp.kalexa.model.JsonRequests.CAN_FULFILL_INTENT_REQUEST
import com.hp.kalexa.model.JsonRequests.CONNECTIONS_REQUEST
import com.hp.kalexa.model.JsonRequests.DISPLAY_SELECTED_REQUEST
import com.hp.kalexa.model.JsonRequests.ERROR_LINK_RESULT
import com.hp.kalexa.model.JsonRequests.INTENT_REQUEST_JSON
import com.hp.kalexa.model.JsonRequests.LAUNCH_REQUEST_JSON
import com.hp.kalexa.model.JsonRequests.WEB_PAGE_SESSION_RESUMED_REQUEST
import com.hp.kalexa.model.connections.print.PrintPDFRequest
import com.hp.kalexa.model.extension.attribute
import com.hp.kalexa.model.json.JacksonSerializer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AlexaRequestEnvelopeTest : Spek({

    given("a incoming json request") {

        on("a Launch Request") {
            val launchIntentRequest = JacksonSerializer.deserialize(LAUNCH_REQUEST_JSON, AlexaRequest::class.java)
            it("should be CustomLaunchRequest type") {
                assert(launchIntentRequest.request is LaunchRequest)
            }
        }

        on("a Intent request") {
            val envelope = JacksonSerializer.deserialize(INTENT_REQUEST_JSON, AlexaRequest::class.java)
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
        on("a SessionResumedRequest") {
            val envelope = JacksonSerializer.deserialize(WEB_PAGE_SESSION_RESUMED_REQUEST, AlexaRequest::class.java)
            it("should be SessionResumedRequest type") {
                assert(envelope.request is SessionResumedRequest)
            }
            val customLinkResultRequest = envelope.request as SessionResumedRequest
            it("should have a ConnectionCompleted payload type") {
                assertEquals("ConnectionCompleted", customLinkResultRequest.cause.type)
            }
            it("should have a success connectionsStatus") {
                assertEquals("200", customLinkResultRequest.cause.status.code)
                assertEquals("OK", customLinkResultRequest.cause.status.message)
            }
            it("should have Token") {
                assertEquals("1234", customLinkResultRequest.cause.token)
            }
        }
        on("a error link result request") {
            val envelope = JacksonSerializer.deserialize(ERROR_LINK_RESULT, AlexaRequest::class.java)
            it("should be CustomLinkResultRequest type") {
                assert(envelope.request is SessionResumedRequest)
            }
            val customLinkResultRequest = envelope.request as SessionResumedRequest

            it("should have an Error connectionsStatus") {
                assertEquals("500", customLinkResultRequest.cause.status.code)
                assertEquals("INTERNAL ERROR", customLinkResultRequest.cause.status.message)
            }
            it("should have ConnectionError type") {
                assertEquals("ConnectionError", customLinkResultRequest.cause.type)
            }
        }
        on("Connections Request") {
            val envelope = JacksonSerializer.deserialize(CONNECTIONS_REQUEST, AlexaRequest::class.java)
            it("should be a LaunchRequest with task object") {
                val launchRequest = envelope.request as LaunchRequest
                assertNotNull(launchRequest.task)
            }
            it("should be a AMAZON.PrintPDF name and version 1") {
                val launchRequest = envelope.request as LaunchRequest
                assertEquals("AMAZON.PrintPDF", launchRequest.task?.name)
                assertEquals("1", launchRequest.task?.version)
            }

            it("should be PrintPDFRequest instance") {
                val launchRequest = envelope.request as LaunchRequest
                assert(launchRequest.task?.input is PrintPDFRequest)
                assertEquals("1", launchRequest.task?.version)
            }
            it("should have title, description and url from input") {
                val launchRequest = envelope.request as LaunchRequest
                val pdfRequest = launchRequest.task?.input as PrintPDFRequest
                assertEquals("Mac & Cheese", pdfRequest.title)
                assertEquals("This is a nice rich mac and cheese. Serve with a salad for a great meatless dinner. Hope you enjoy it", pdfRequest.description)
                assertEquals("http://allrecipes.com/recipe/11679/homemade-mac-and-cheese/", pdfRequest.url)
            }
        }
        on("a display element element request") {
            val envelope = JacksonSerializer.deserialize(DISPLAY_SELECTED_REQUEST, AlexaRequest::class.java)
            it("should be CustomElementSelectedRequest type") {
                assert(envelope.request is ElementSelectedRequest)
            }
            val customElementSelectedRequest = envelope.request as ElementSelectedRequest
            it("should have a token attribute in the request") {
                assertEquals("TeamsIntent\\|Internacional", customElementSelectedRequest.token)
            }
        }

        on("CanFulfillIntentRequest") {
            val envelope = JacksonSerializer.deserialize(CAN_FULFILL_INTENT_REQUEST, AlexaRequest::class.java)
            it("should be CanFulfillIntentRequest type") {
                assert(envelope.request is CanFulfillIntentRequest)
            }
            val canFulfillIntentRequest = envelope.request as CanFulfillIntentRequest
            it("should have intent name PlaySound") {
                assertEquals("PlaySound", canFulfillIntentRequest.intent.name)
            }
            it("should have a crickets value in Sound slot") {
                assertEquals(1, canFulfillIntentRequest.intent.slots.size)
                val slot = canFulfillIntentRequest.intent.getSlot("Sound")
                assertEquals("crickets", slot?.value)
            }
        }
    }
})