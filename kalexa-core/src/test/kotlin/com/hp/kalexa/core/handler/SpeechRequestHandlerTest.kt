package com.hp.kalexa.core.handler

import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.response.AlexaResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.objectMockk
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object SpeechRequestHandlerTest : Spek({

    describe("Test Speech Request Handler with a mocked SpeechHandler implementation") {
        context("When handleRequestType is called by process method") {
            val alexaResponse = mockk<AlexaResponse>()
            val speechHandler = mockk<SpeechHandler> {
                every { handleSessionStartedRequest(any()) } returns alexaResponse
                every { handleLaunchRequest(any()) } returns alexaResponse
                every { handleIntentRequest(any()) } returns alexaResponse
                every { handleConnectionsResponseRequest(any()) } returns alexaResponse
                every { handleConnectionsRequest(any()) } returns alexaResponse
                every { handleSessionEndedRequest(any()) } returns alexaResponse
                every { handleElementSelectedRequest(any()) } returns alexaResponse
            }
            objectMockk(AlexaRequestEnvelope).mock()
            objectMockk(Util).mock()
            lateinit var speechRequestHandler: SpeechRequestHandler
            beforeEachTest {
                speechRequestHandler = SpeechRequestHandler(speechHandler)
            }

            on("HandleRequestType") {
                every { Util.getApplicationID() } returns "123456"
                it("should handle SessionStartedRequest") {
                    val sessionStartedEnvelope = mockk<AlexaRequestEnvelope<SessionStartedRequest>> {
                        every { request } returns mockk<SessionStartedRequest>()
                        every { session.application?.applicationId } returns "123456"
                    }
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns sessionStartedEnvelope
                    val expected = "SessionStartedRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle LaunchRequest") {
                    val launchRequestEnvelope = mockk<AlexaRequestEnvelope<LaunchRequest>> {
                        every { request } returns mockk<LaunchRequest>()
                        every { session.application?.applicationId } returns "123456"
                    }
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns launchRequestEnvelope
                    val expected = "LaunchRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle IntentRequest") {
                    val intentRequestEnvelope = mockk<AlexaRequestEnvelope<IntentRequest>> {
                        every { request } returns mockk<IntentRequest>()
                        every { session.application?.applicationId } returns "123456"
                    }
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns intentRequestEnvelope
                    val expected = "IntentRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ConnectionsResponseRequest") {
                    val connectionsResponseEnvelope = mockk<AlexaRequestEnvelope<ConnectionsResponseRequest>> {
                        every { request } returns mockk<ConnectionsResponseRequest>()
                        every { session.application?.applicationId } returns "123456"
                    }
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns connectionsResponseEnvelope
                    val expected = "ConnectionsResponseRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ConnectionsRequest") {
                    val connectionsRequestEnvelope = mockk<AlexaRequestEnvelope<ConnectionsRequest>> {
                        every { request } returns mockk<ConnectionsRequest>()
                        every { session.application?.applicationId } returns "123456"
                    }
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns connectionsRequestEnvelope
                    val expected = "ConnectionsRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle SessionEndedRequest") {
                    val sessionEndedEnvelope = mockk<AlexaRequestEnvelope<SessionEndedRequest>> {
                        every { request } returns mockk<SessionEndedRequest>()
                        every { session.application?.applicationId } returns "123456"
                    }
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns sessionEndedEnvelope
                    val expected = "SessionEndedRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
                it("should handle ElementSelectedRequest") {
                    val envelope = mockk<AlexaRequestEnvelope<ElementSelectedRequest>> {
                        every { request } returns mockk<ElementSelectedRequest>()
                        every { session.application?.applicationId } returns "123456"
                    }
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns envelope
                    val expected = "ElementSelectedRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }

                it("should return empty response") {
                    val envelope = mockk<AlexaRequestEnvelope<*>> {
                        every { request } returns mockk<ElementSelectedRequest>()
                        every { session.application?.applicationId } returns "123456"
                    }
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns envelope
                    val expected = "ElementSelectedRequest"
                    every { alexaResponse.toJson() } returns expected
                    val response = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(expected, response)
                }
            }
        }
        context("When validateApplicationId is called by process method") {
            val json = "{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"Lorem ipsum dolor sit amet\"}}"
            val response = mockk<AlexaResponse> {
                every { toJson() } returns json
            }
            val launchRequest = mockk<LaunchRequest>()
            val requestEnvelope = mockk<AlexaRequestEnvelope<LaunchRequest>> {
                every { request } returns launchRequest
                every { session.application?.applicationId } returns "123456"
            }
            val handler = mockk<SpeechHandler> {
                every { handleLaunchRequest(requestEnvelope) } returns response
            }
            objectMockk(AlexaRequestEnvelope).mock()
            objectMockk(Util).mock()
            val speechRequestHandler = SpeechRequestHandler(handler)

            on("Application Id in not defined in environment variable") {
                every { Util.getApplicationID() } returns null
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) { speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray()) }
                }
            }

            on("Valid Application Id in session.application.applicationId") {
                every { Util.getApplicationID() } returns "123456"
                it("should process the requestEnvelope without errors") {
                    println(requestEnvelope.request)
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns requestEnvelope
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
                every { requestEnvelope.session.application?.applicationId } returns null
                every { requestEnvelope.context.system.application.applicationId } returns "123456"
                every { Util.getApplicationID() } returns "123456"
                it("should process the requestEnvelope without errors") {
                    every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns requestEnvelope
                    val resp = speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray())
                    assertEquals(json, resp)
                }
            }

            on("Invalid Application Id in context.system.application.applicationId") {
                every { requestEnvelope.session.application?.applicationId } returns null
                every { requestEnvelope.context.system.application.applicationId } returns "123456"
                every { Util.getApplicationID() } returns "654321"
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) { speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray()) }
                }
            }

            on("No Application Id in context.system.application.applicationId") {
                every { requestEnvelope.session.application?.applicationId } returns null
                every { requestEnvelope.context.system.application.applicationId } returns null
                every { Util.getApplicationID() } returns "123456"
                every { AlexaRequestEnvelope.fromJson(any<ByteArray>()) } returns requestEnvelope
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) { speechRequestHandler.process("Lorem ipsum dolor sit amet".toByteArray()) }
                }
            }
        }
    }
})