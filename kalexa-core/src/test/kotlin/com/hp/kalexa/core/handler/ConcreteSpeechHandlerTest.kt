/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.model.FakeIntent
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Intent
import com.hp.kalexa.model.Session
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.request.ConnectionsRequest
import com.hp.kalexa.model.request.ConnectionsResponseRequest
import com.hp.kalexa.model.request.ElementSelectedRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
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
import io.mockk.unmockkObject
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object ConcreteSpeechHandlerTest : Spek({

    describe("a Concrete Speech Handler class") {
        lateinit var concreteSpeechHandler: ConcreteSpeechHandler
        beforeEachTest {
            mockkObject(Util)
        }
        afterEachTest {
            unmockkObject(Util)
        }

        describe("When handleSessionStarted method is called") {
            val envelope = mockk<AlexaRequest<SessionStartedRequest>>()
            every { Util.getScanPackage() } returns "com.hp.kalexa.core.model"

            concreteSpeechHandler = ConcreteSpeechHandler()
            it("should return an empty response") {
                val alexaResponse = concreteSpeechHandler.handleSessionStartedRequest(envelope)
                assertEquals(AlexaResponse.emptyResponse().toJson(), alexaResponse.toJson())
            }
        }
        describe("When handleLaunchRequest method is called") {
            val customLaunchRequestEnvelope = mockk<AlexaRequest<LaunchRequest>>()
            beforeGroup {
                val session = mockk<Session>()
                val context = mockk<Context>()
                val launchRequest = mockk<LaunchRequest>()
                val version = "1.0"
                every { customLaunchRequestEnvelope.session } returns session
                every { customLaunchRequestEnvelope.context } returns context
                every { customLaunchRequestEnvelope.version } returns version
                every { customLaunchRequestEnvelope.request } returns launchRequest
                every { session.attributes } returns mutableMapOf()
            }
            on("Without implementing LaunchIntentHandler") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                concreteSpeechHandler = ConcreteSpeechHandler()

                it("should return the default Launch response") {
                    val alexaResponse = concreteSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope)
                    assertEquals(IntentUtil.defaultGreetings().toJson(), alexaResponse.toJson())
                }
            }
            on("With LaunchIntentHandler implemented ") {
                val fakeIntent = mockk<FakeIntent>()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return the onLaunchIntent response from the class implementing LaunchIntentHandler") {
                    val alexaResponse = concreteSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a hello from FakeIntent@onLaunchIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
        describe("When handleIntentRequest method is called") {
            val intentRequestEnvelope = mockk<AlexaRequest<IntentRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: IntentRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { intentRequestEnvelope.session } returns session
                every { intentRequestEnvelope.request } returns request
                every { intentRequestEnvelope.context } returns context
                every { intentRequestEnvelope.version } returns "1.0"
            }
            context("Custom Intent") {
                on("Existent Custom Intent") {
                    every { intentRequestEnvelope.request.intent.name } returns "FakeIntent"
                    concreteSpeechHandler = ConcreteSpeechHandler()
                    it("should call onIntentRequest method") {
                        val response = concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(
                            """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a hello from FakeIntent"},"card":{"type":"Simple","title":"Hello world","content":"This is a content coming from FakeIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                            response.toJson()
                        )
                    }
                }
                on("Non existent Custom Intent") {
                    every { intentRequestEnvelope.request.intent.name } returns "UnknownIntent"
                    it("should throw IllegalArgumentException") {
                        assertFailsWith(exceptionClass = IllegalArgumentException::class) {
                            concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        }
                    }
                }
            }
            context("Amazon FallbackIntent") {
                on("with FallbackIntentHandler implemented") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.FallbackIntent"
                    concreteSpeechHandler = ConcreteSpeechHandler()
                    it("should call FakeIntent fallback method") {
                        val response = concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(
                            """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a fallback response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                            response.toJson()
                        )
                    }
                }
                on("without FallbackIntentHandler implemented") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.FallbackIntent"
                    every { Util.loadClassesFromPackage() } returns emptySet()
                    concreteSpeechHandler = ConcreteSpeechHandler()
                    it("should call default fallback method") {
                        val response = concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(
                            IntentUtil.unsupportedIntent().toJson(),
                            response.toJson()
                        )
                    }
                }
            }
            context("Amazon Help Intent") {

                on("with HelpIntentHandler implemented") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.HelpIntent"
                    concreteSpeechHandler = ConcreteSpeechHandler()
                    it("should call FakeIntent onHelpIntent method") {
                        val response = concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(
                            """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a help response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                            response.toJson()
                        )
                    }
                }
                on("without HelpIntentHandler implemented") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.HelpIntent"
                    every { Util.loadClassesFromPackage() } returns emptySet()
                    concreteSpeechHandler = ConcreteSpeechHandler()
                    it("should call default helpIntent method") {
                        val response = concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(
                            IntentUtil.helpIntent().toJson(),
                            response.toJson()
                        )
                    }
                }
            }
            context("On Built In Intent") {
                on("Yes Built in Intent with intent context locked") {
                    every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                    attributesSession[SpeechHandler.INTENT_CONTEXT] = "FakeIntent"
                    concreteSpeechHandler = ConcreteSpeechHandler()
                    it("should call onBuiltInIntent method") {
                        val response = concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(
                            """{"response":{"outputSpeech":{"type":"PlainText","text":"I'm sorry, I couldn't understand what you have said. Could you say it again?"},"directives":[],"shouldEndSession":false},"sessionAttributes":{"com.hp.kalexa.intentContext":"FakeIntent","retry":1},"version":"1.0"}""",
                            response.toJson()
                        )
                    }
                }
                on("Unknown Intent Context locked to Yes Built in intent") {
                    attributesSession[SpeechHandler.INTENT_CONTEXT] = "UnknownIntent"
                    every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                    concreteSpeechHandler = ConcreteSpeechHandler()
                    it("should throw IllegalArgumentException") {
                        assertFailsWith(exceptionClass = IllegalArgumentException::class) {
                            concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        }
                    }
                }
                context("Yes Built in Intent without context locked") {
                    on("with RecoverIntentContextHandler implemented") {
                        every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                        concreteSpeechHandler = ConcreteSpeechHandler()
                        it("should should call unknownIntentContext") {
                            val response = concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                            assertEquals(
                                """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a unknown intent context response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                                response.toJson()
                            )
                        }
                    }
                    on("without RecoverIntentContextHandler implemented") {
                        every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                        every { Util.loadClassesFromPackage() } returns emptySet()
                        concreteSpeechHandler = ConcreteSpeechHandler()
                        it("should should call default unknownIntentContext response") {
                            val response = concreteSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                            assertEquals(
                                """{"response":{"outputSpeech":{"type":"PlainText","text":"I'm sorry, I couldn't understand what you have said. Could you say it again?"},"directives":[],"shouldEndSession":false},"sessionAttributes":{"retry":1},"version":"1.0"}""",
                                response.toJson()
                            )
                        }
                    }
                }
            }
        }
        describe("When handleElementSelectedRequest method is called") {
            val elementSelectedRequest = mockk<AlexaRequest<ElementSelectedRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ElementSelectedRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk {
                    every { token } returns "FakeIntent"
                }
                every { elementSelectedRequest.session } returns session
                every { elementSelectedRequest.request } returns request
                every { elementSelectedRequest.context } returns context
                every { elementSelectedRequest.version } returns "1.0"
            }
            on("Element Selected with intent context locked") {
                attributesSession[SpeechHandler.INTENT_CONTEXT] = "FakeIntent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                val response = concreteSpeechHandler.handleElementSelectedRequest(elementSelectedRequest)
                it("should use value of Intent Context to call FakeIntent.onElementSelected and return an empty response") {
                    assertEquals(
                        "{\"response\":{\"directives\":[]},\"sessionAttributes\":{\"com.hp.kalexa.intentContext\":\"FakeIntent\"},\"version\":\"1.0\"}",
                        response.toJson()
                    )
                }
            }
            on("Element Selected with intent context locked and map to an unknown intent") {
                attributesSession[SpeechHandler.INTENT_CONTEXT] = "UnknownIntent"
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) {
                        concreteSpeechHandler.handleElementSelectedRequest(elementSelectedRequest)
                    }
                }
            }
            on("Element Selected with intent context UNLOCKED") {
                concreteSpeechHandler = ConcreteSpeechHandler()
                val response = concreteSpeechHandler.handleElementSelectedRequest(elementSelectedRequest)
                it("should use value of token to call FakeIntent.onElementSelected and return an empty response") {
                    assertEquals(
                        "{\"response\":{\"directives\":[]},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson()
                    )
                }
            }
        }
        describe("When handleConnectionsResponseRequest method is called") {
            val connectionsResponseRequest = mockk<AlexaRequest<ConnectionsResponseRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ConnectionsResponseRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk {
                    every { token } returns "FakeIntent"
                }
                every { connectionsResponseRequest.session } returns session
                every { connectionsResponseRequest.request } returns request
                every { connectionsResponseRequest.context } returns context
                every { connectionsResponseRequest.version } returns "1.0"
            }

            on("with RequesterHandler implemented") {
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should call onConnectionsResponse method") {
                    val response = concreteSpeechHandler.handleConnectionsResponseRequest(connectionsResponseRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a onConnectionsResponse from FakeIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        response.toJson()
                    )
                }
            }
            on("without RequesterHandler implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should call default onConnectionsResponse method") {
                    val response = concreteSpeechHandler.handleConnectionsResponseRequest(connectionsResponseRequest)
                    assertEquals(
                        AlexaResponse.emptyResponse().toJson(),
                        response.toJson()
                    )
                }
            }
        }
        describe("When handleConnectionsRequest method is called") {
            val connectionsRequest = mockk<AlexaRequest<ConnectionsRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ConnectionsRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { connectionsRequest.session } returns session
                every { connectionsRequest.request } returns request
                every { connectionsRequest.context } returns context
                every { connectionsRequest.version } returns "1.0"
            }

            on("without ProviderHandler implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                every { Util.getScanPackage() } returns "package.with.no.intent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a default Provider response") {
                    val alexaResponse = concreteSpeechHandler.handleConnectionsRequest(connectionsRequest)
                    assertEquals(IntentUtil.unsupportedIntent().toJson(), alexaResponse.toJson())
                }
            }
            on("with ProviderHandler implemented") {
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should call onConnectionsRequest method") {
                    val alexaResponse = concreteSpeechHandler.handleConnectionsRequest(connectionsRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a onConnectionsRequest from FakeIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
        describe("When handleListCreatedEventRequest method is called") {
            val listCreatedEventRequest = mockk<AlexaRequest<ListCreatedEventRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ListCreatedEventRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { listCreatedEventRequest.session } returns session
                every { listCreatedEventRequest.request } returns request
                every { listCreatedEventRequest.context } returns context
                every { listCreatedEventRequest.version } returns "1.0"
            }

            on("without ListEventsHandler implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                every { Util.getScanPackage() } returns "package.with.no.intent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a default ListEvents response") {
                    val alexaResponse = concreteSpeechHandler.handleListCreatedEventRequest(listCreatedEventRequest)
                    assertEquals(IntentUtil.unsupportedIntent().toJson(), alexaResponse.toJson())
                }
            }
            on("with ListEventsHandler implemented") {
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should call onListCreatedEventRequest method") {
                    val alexaResponse = concreteSpeechHandler.handleListCreatedEventRequest(listCreatedEventRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a ListCreatedEventRequest response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
        describe("When handleListUpdatedEventRequest method is called") {
            val listUpdatedEventRequest = mockk<AlexaRequest<ListUpdatedEventRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ListUpdatedEventRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { listUpdatedEventRequest.session } returns session
                every { listUpdatedEventRequest.request } returns request
                every { listUpdatedEventRequest.context } returns context
                every { listUpdatedEventRequest.version } returns "1.0"
            }

            on("without ListEventsHandler implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                every { Util.getScanPackage() } returns "package.with.no.intent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a default ListEvents response") {
                    val alexaResponse = concreteSpeechHandler.handleListUpdatedEventRequest(listUpdatedEventRequest)
                    assertEquals(IntentUtil.unsupportedIntent().toJson(), alexaResponse.toJson())
                }
            }
            on("with ListEventsHandler implemented") {
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should call onListUpdatedEventRequest method") {
                    val alexaResponse = concreteSpeechHandler.handleListUpdatedEventRequest(listUpdatedEventRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a ListUpdatedEventRequest response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
        describe("When handleListDeletedEventRequest method is called") {
            val listDeletedEventRequest = mockk<AlexaRequest<ListDeletedEventRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ListDeletedEventRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { listDeletedEventRequest.session } returns session
                every { listDeletedEventRequest.request } returns request
                every { listDeletedEventRequest.context } returns context
                every { listDeletedEventRequest.version } returns "1.0"
            }

            on("without ListEventsHandler implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                every { Util.getScanPackage() } returns "package.with.no.intent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a default ListEvents response") {
                    val alexaResponse = concreteSpeechHandler.handleListDeletedEventRequest(listDeletedEventRequest)
                    assertEquals(IntentUtil.unsupportedIntent().toJson(), alexaResponse.toJson())
                }
            }
            on("with ListEventsHandler implemented") {
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should call onListDeletedEventRequest method") {
                    val alexaResponse = concreteSpeechHandler.handleListDeletedEventRequest(listDeletedEventRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a ListDeletedEventRequest response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
        describe("When handleListItemsCreatedEventRequest method is called") {
            val listItemsCreatedEventRequest = mockk<AlexaRequest<ListItemsCreatedEventRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ListItemsCreatedEventRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { listItemsCreatedEventRequest.session } returns session
                every { listItemsCreatedEventRequest.request } returns request
                every { listItemsCreatedEventRequest.context } returns context
                every { listItemsCreatedEventRequest.version } returns "1.0"
            }

            on("without ListEventsHandler implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                every { Util.getScanPackage() } returns "package.with.no.intent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a default ListEvents response") {
                    val alexaResponse =
                        concreteSpeechHandler.handleListItemsCreatedEventRequest(listItemsCreatedEventRequest)
                    assertEquals(IntentUtil.unsupportedIntent().toJson(), alexaResponse.toJson())
                }
            }
            on("with ListEventsHandler implemented") {
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should call onListItemsCreatedEventRequest method ") {
                    val alexaResponse =
                        concreteSpeechHandler.handleListItemsCreatedEventRequest(listItemsCreatedEventRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a ListItemsCreatedEventRequest response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
        describe("When handleListItemsUpdatedEventRequest method is called") {
            val listItemsUpdatedEventRequest = mockk<AlexaRequest<ListItemsUpdatedEventRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ListItemsUpdatedEventRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { listItemsUpdatedEventRequest.session } returns session
                every { listItemsUpdatedEventRequest.request } returns request
                every { listItemsUpdatedEventRequest.context } returns context
                every { listItemsUpdatedEventRequest.version } returns "1.0"
            }

            on("without ListEventsHandler implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                every { Util.getScanPackage() } returns "package.with.no.intent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a default ListEvents response") {
                    val alexaResponse =
                        concreteSpeechHandler.handleListItemsUpdatedEventRequest(listItemsUpdatedEventRequest)
                    assertEquals(IntentUtil.unsupportedIntent().toJson(), alexaResponse.toJson())
                }
            }
            on("with ListEventsHandler implemented") {
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should call onListItemsUpdatedEventRequest method") {
                    val alexaResponse =
                        concreteSpeechHandler.handleListItemsUpdatedEventRequest(listItemsUpdatedEventRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a ListItemsUpdatedEventRequest response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
        describe("When handleListItemsDeletedEventRequest method is called") {
            val listItemsDeletedEventRequest = mockk<AlexaRequest<ListItemsDeletedEventRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: ListItemsDeletedEventRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                every { Util.getScanPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { listItemsDeletedEventRequest.session } returns session
                every { listItemsDeletedEventRequest.request } returns request
                every { listItemsDeletedEventRequest.context } returns context
                every { listItemsDeletedEventRequest.version } returns "1.0"
            }

            on("without ListEventsHandler implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                every { Util.getScanPackage() } returns "package.with.no.intent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a default ListEvents response") {
                    val alexaResponse =
                        concreteSpeechHandler.handleListItemsDeletedEventRequest(listItemsDeletedEventRequest)
                    assertEquals(IntentUtil.unsupportedIntent().toJson(), alexaResponse.toJson())
                }
            }
            on("with ListEventsHandler implemented") {
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a call onListItemsDeletedEventRequest method from the intent annotated with ListEvents") {
                    val alexaResponse =
                        concreteSpeechHandler.handleListItemsDeletedEventRequest(listItemsDeletedEventRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a ListItemsDeletedEventRequest response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
        describe("When handleCanFulfillIntentRequest method is called") {
            val canFulfillIntentRequest = mockk<AlexaRequest<CanFulfillIntentRequest>>()
            lateinit var fakeIntent: BaseHandler
            lateinit var context: Context
            lateinit var request: CanFulfillIntentRequest
            lateinit var session: Session
            lateinit var intent: Intent
            val attributesSession = mutableMapOf<String, Any>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                intent = mockk()
                every { canFulfillIntentRequest.session } returns session
                every { canFulfillIntentRequest.request } returns request
                every { canFulfillIntentRequest.context } returns context
                every { canFulfillIntentRequest.version } returns "1.0"
                every { request.intent } returns intent
                every { intent.name } returns "FakeIntent"
            }

            on("without CanFulfillIntentRequest implemented") {
                every { Util.loadClassesFromPackage() } returns emptySet()
                every { Util.getScanPackage() } returns "package.with.no.intent"
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) {
                        concreteSpeechHandler.handleCanFulfillIntentRequest(canFulfillIntentRequest)
                    }
                }
            }
            on("with CanFulfillIntentRequest implemented") {
                every { Util.getScanPackage() } returns "package.with.intent"
                every { Util.loadClassesFromPackage() } returns setOf(fakeIntent::class)
                concreteSpeechHandler = ConcreteSpeechHandler()
                it("should return a response from the onCanFulfillIntent method ") {
                    val alexaResponse =
                        concreteSpeechHandler.handleCanFulfillIntentRequest(canFulfillIntentRequest)
                    assertEquals(
                        """{"response":{"outputSpeech":{"type":"PlainText","text":"This is a hello from FakeIntent@onCanFulfillIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                        alexaResponse.toJson()
                    )
                }
            }
        }
    }
})