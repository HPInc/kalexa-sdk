/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.json.JacksonSerializer
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.SessionEndedRequest
import com.hp.kalexa.model.request.SessionResumedRequest
import com.hp.kalexa.model.request.SessionStartedRequest
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.apache.logging.log4j.LogManager
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object SpeechRequestHandlerTest : Spek({
    val logger = LogManager.getLogger(SpeechRequestHandlerTest::class.java)

    describe("Test Speech Request Handler with a mocked SpeechHandler implementation") {
        context("When handleRequestType is called by process method") {
            val alexaResponse = mockk<AlexaResponse>()
            val delegator = mockk<RequestTypeDelegator> {
                every { delegate(any()) } returns alexaResponse
                // every { handleSessionStartedRequest(any()) } returns alexaResponse
                // every { handleLaunchRequest(any()) } returns alexaResponse
                // every { handleIntentRequest(any()) } returns alexaResponse
                // every { handleSessionResumedRequest(any()) } returns alexaResponse
                // every { handleSessionEndedRequest(any()) } returns alexaResponse
                // every { handleElementSelectedRequest(any()) } returns alexaResponse
                // every { handleListCreatedEventRequest(any()) } returns alexaResponse
                // every { handleListUpdatedEventRequest(any()) } returns alexaResponse
                // every { handleListDeletedEventRequest(any()) } returns alexaResponse
                // every { handleListItemsCreatedEventRequest(any()) } returns alexaResponse
                // every { handleListItemsUpdatedEventRequest(any()) } returns alexaResponse
                // every { handleListItemsDeletedEventRequest(any()) } returns alexaResponse
            }
            mockkObject(JacksonSerializer)
            mockkObject(Util)
            lateinit var speechRequestHandler: SpeechRequestHandler
            beforeEachTest {
                speechRequestHandler = SpeechRequestHandler(SkillConfig(), delegator)
            }

            on("HandleRequestType") {
                every { Util.getApplicationID() } returns "123456"
                it("should handle SessionStartedRequest") {
                    val sessionStartedEnvelope = mockk<AlexaRequest<SessionStartedRequest>> {
                        every { request } returns mockk<SessionStartedRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every {
                        JacksonSerializer.deserialize(
                            any<ByteArray>(),
                            AlexaRequest::class.java
                        )
                    } returns sessionStartedEnvelope
                    val expected = "SessionStartedRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle LaunchRequest") {
                    val launchRequestEnvelope = mockk<AlexaRequest<LaunchRequest>> {
                        every { request } returns mockk<LaunchRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every {
                        JacksonSerializer.deserialize(
                            any<ByteArray>(),
                            AlexaRequest::class.java
                        )
                    } returns launchRequestEnvelope
                    val expected = "LaunchRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle IntentRequest") {
                    val intentRequestEnvelope = mockk<AlexaRequest<IntentRequest>> {
                        every { request } returns mockk<IntentRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every {
                        JacksonSerializer.deserialize(
                            any<ByteArray>(),
                            AlexaRequest::class.java
                        )
                    } returns intentRequestEnvelope
                    val expected = "IntentRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ConnectionsResponseRequest") {
                    val connectionsResponseEnvelope = mockk<AlexaRequest<SessionResumedRequest>> {
                        every { request } returns mockk<SessionResumedRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every {
                        JacksonSerializer.deserialize(
                            any<ByteArray>(),
                            AlexaRequest::class.java
                        )
                    } returns connectionsResponseEnvelope
                    val expected = "ConnectionsResponseRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle SessionEndedRequest") {
                    val sessionEndedEnvelope = mockk<AlexaRequest<SessionEndedRequest>> {
                        every { request } returns mockk<SessionEndedRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every {
                        JacksonSerializer.deserialize(
                            any<ByteArray>(),
                            AlexaRequest::class.java
                        )
                    } returns sessionEndedEnvelope
                    val expected = "SessionEndedRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ElementSelectedRequest") {
                    val envelope = mockk<AlexaRequest<ElementSelectedRequest>> {
                        every { request } returns mockk<ElementSelectedRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every { JacksonSerializer.deserialize(any<ByteArray>(), AlexaRequest::class.java) } returns envelope
                    val expected = "ElementSelectedRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }

                it("should handle ListCreatedEventRequest") {
                    val envelope = mockk<AlexaRequest<ListCreatedEventRequest>> {
                        every { request } returns mockk<ListCreatedEventRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every { JacksonSerializer.deserialize(any<ByteArray>(), AlexaRequest::class.java) } returns envelope
                    val expected = "ListCreatedEventRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ListUpdatedEventRequest") {
                    val envelope = mockk<AlexaRequest<ListUpdatedEventRequest>> {
                        every { request } returns mockk<ListUpdatedEventRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every { JacksonSerializer.deserialize(any<ByteArray>(), AlexaRequest::class.java) } returns envelope
                    val expected = "ListUpdatedEventRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ListDeletedEventRequest") {
                    val envelope = mockk<AlexaRequest<ListDeletedEventRequest>> {
                        every { request } returns mockk<ListDeletedEventRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every { JacksonSerializer.deserialize(any<ByteArray>(), AlexaRequest::class.java) } returns envelope
                    val expected = "ListDeletedEventRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ListItemsCreatedEventRequest") {
                    val envelope = mockk<AlexaRequest<ListItemsCreatedEventRequest>> {
                        every { request } returns mockk<ListItemsCreatedEventRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every { JacksonSerializer.deserialize(any<ByteArray>(), AlexaRequest::class.java) } returns envelope
                    val expected = "ListItemsCreatedEventRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ListItemsUpdatedEventRequest") {
                    val envelope = mockk<AlexaRequest<ListItemsUpdatedEventRequest>> {
                        every { request } returns mockk<ListItemsUpdatedEventRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every { JacksonSerializer.deserialize(any<ByteArray>(), AlexaRequest::class.java) } returns envelope
                    val expected = "ListItemsUpdatedEventRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ListItemsDeletedEventRequest") {
                    val envelope = mockk<AlexaRequest<ListItemsDeletedEventRequest>> {
                        every { request } returns mockk<ListItemsDeletedEventRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every { JacksonSerializer.deserialize(any<ByteArray>(), AlexaRequest::class.java) } returns envelope
                    val expected = "ListItemsDeletedEventRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }

                it("should return empty response") {
                    val envelope = mockk<AlexaRequest<*>> {
                        every { request } returns mockk<ElementSelectedRequest>()
                        every { session?.application?.applicationId } returns "123456"
                    }
                    every { JacksonSerializer.deserialize(any<ByteArray>(), AlexaRequest::class.java) } returns envelope
                    val expected = "ElementSelectedRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
            }
        }
        context("When validateApplicationId is called by process method") {
            val json =
                "{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"Lorem ipsum dolor sit amet\"}}"
            val response = mockk<AlexaResponse> {
                every { toJson() } returns json
            }
            val launchRequest = mockk<LaunchRequest>()
            val requestEnvelope = mockk<AlexaRequest<LaunchRequest>> {
                every { request } returns launchRequest
                every { session?.application?.applicationId } returns "123456"
            }
            val handler = mockk<RequestTypeDelegator> {
                every { delegate(any()) } returns response
            }
            mockkObject(JacksonSerializer)
            mockkObject(Util)
            val speechRequestHandler = SpeechRequestHandler(requestTypeDelegator = handler)

            on("Application Id in not defined in environment variable") {
                every { Util.getApplicationID() } returns null
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) { speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray()) }
                }
            }

            on("Valid Application Id in session.application.applicationId") {
                every { Util.getApplicationID() } returns "123456"
                it("should process the requestEnvelope without errors") {
                    logger.debug(requestEnvelope.request)
                    every {
                        JacksonSerializer.deserialize(
                            any<ByteArray>(),
                            AlexaRequest::class.java
                        )
                    } returns requestEnvelope
                    val resp = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(json, resp)
                }
            }

            on("Invalid Application Id in session.application.applicationId") {
                every { Util.getApplicationID() } returns "654321"
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) { speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray()) }
                }
            }

            on("Valid Application Id in context.system.application.applicationId") {
                every { requestEnvelope.session?.application?.applicationId } returns null
                every { requestEnvelope.context.system.application.applicationId } returns "123456"
                every { Util.getApplicationID() } returns "123456"
                it("should process the requestEnvelope without errors") {
                    every {
                        JacksonSerializer.deserialize(
                            any<ByteArray>(),
                            AlexaRequest::class.java
                        )
                    } returns requestEnvelope
                    val resp = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(json, resp)
                }
            }

            on("Invalid Application Id in context.system.application.applicationId") {
                every { requestEnvelope.session?.application?.applicationId } returns null
                every { requestEnvelope.context.system.application.applicationId } returns "123456"
                every { Util.getApplicationID() } returns "654321"
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) { speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray()) }
                }
            }

            on("No Application Id in context.system.application.applicationId") {
                every { requestEnvelope.session?.application?.applicationId } returns null
                every { requestEnvelope.context.system.application.applicationId } returns null
                every { Util.getApplicationID() } returns "123456"
                every {
                    JacksonSerializer.deserialize(
                        any<ByteArray>(),
                        AlexaRequest::class.java
                    )
                } returns requestEnvelope
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) { speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray()) }
                }
            }
        }
    }
})
