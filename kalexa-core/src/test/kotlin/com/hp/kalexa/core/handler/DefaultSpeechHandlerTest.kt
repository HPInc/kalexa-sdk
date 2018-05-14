package com.hp.kalexa.core.handler

import com.hp.kalexa.core.annotation.*
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.IntentExecutor
import com.hp.kalexa.core.model.FakeIntent
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Session
import com.hp.kalexa.model.request.*
import com.hp.kalexa.model.response.AlexaResponse
import com.sun.xml.internal.txw2.IllegalAnnotationException
import io.mockk.every
import io.mockk.mockk
import io.mockk.objectMockk
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object DefaultSpeechHandlerTest : Spek({

    describe("a Default speech handler class") {
        val defaultSpeechHandler by memoized { DefaultSpeechHandler() }
        objectMockk(Util).mock()
        every { Util.getIntentPackage() } returns "com.hp.kalexa.core.model"

        describe("When handleSessionStarted method is called") {
            val envelope = mockk<AlexaRequestEnvelope<SessionStartedRequest>>()
            it("should return an empty response") {
                val alexaResponse = defaultSpeechHandler.handleSessionStartedRequest(envelope)
                assertEquals(AlexaResponse.emptyResponse().toJson(), alexaResponse.toJson())
            }
        }

        describe("When handleLaunchRequest method is called") {
            val customLaunchRequestEnvelope = mockk<AlexaRequestEnvelope<LaunchRequest>>()
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
            on("Intent without LaunchIntent annotation") {
                it("should return a default Launch response") {
                    every { Util.getIntentPackage() } returns "package.with.no.intent"
                    val alexaResponse = defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope)
                    assertEquals(IntentUtil.defaultGreetings().toJson(), alexaResponse.toJson())
                }
            }
            on("Intent with LaunchIntent annotation") {
                every { Util.getIntentPackage() } returns "package.with.intent"
                val fakeIntent = mockk<FakeIntent>()
                every { Util.loadIntentClassesFromPackage() } returns listOf(fakeIntent::class)
                every { Util.findAnnotatedClasses(any(), LaunchIntent::class) } returns listOf(fakeIntent::class)

                it("should return a call onLaunchIntent method from the intent annotated with launcher") {
                    val alexaResponse = defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope)
                    assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a hello from FakeIntent@onLaunchIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                            alexaResponse.toJson())
                }
            }
            on("More than one Intent with @LaunchIntent annotation") {
                val intentExecutor = mockk<KClass<out IntentExecutor>>()
                val intentExecutor2 = mockk<KClass<out IntentExecutor>>()
                every { Util.findAnnotatedClasses(any(), LaunchIntent::class) } returns listOf(intentExecutor, intentExecutor2)
                it("should throw illegal annotation argument") {
                    assertFailsWith(exceptionClass = IllegalAnnotationException::class) { defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope) }
                }
            }
        }

        describe("When handleIntentRequest method is called") {
            val intentRequestEnvelope = mockk<AlexaRequestEnvelope<IntentRequest>>()
            lateinit var fakeIntent: IntentExecutor
            lateinit var context: Context
            lateinit var request: IntentRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any?>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadIntentClassesFromPackage() } returns listOf(fakeIntent::class)
                every { Util.getIntentPackage() } returns "package.with.intent"
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
                    it("should call onIntentRequest method") {
                        val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a hello from FakeIntent"},"card":{"type":"Simple","title":"Hello world","content":"This is a content coming from FakeIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}"""
                                , response.toJson())
                    }
                }
                on("Non existent Custom Intent") {
                    every { intentRequestEnvelope.request.intent.name } returns "UnknownIntent"
                    it("should throw IllegalArgumentException") {
                        assertFailsWith(exceptionClass = IllegalArgumentException::class) {
                            defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        }
                    }
                }
            }
            context("Amazon FallbackIntent Intent") {
                beforeGroup {
                    every { Util.findAnnotatedClasses(any(), FallbackIntent::class) } returns listOf(fakeIntent::class)
                }
                on("FallbackIntent Intent with @FallbackIntent annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.FallbackIntent"
                    it("should call FakeIntent fallback method") {
                        val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a fallback response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                                response.toJson())
                    }
                }
                on("FallbackIntent Intent without @FallbackIntent annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.FallbackIntent"
                    every { Util.loadIntentClassesFromPackage() } returns emptyList()
                    every { Util.findAnnotatedClasses(any(), FallbackIntent::class) } returns emptyList()
                    it("should call default fallback method") {
                        val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(IntentUtil.unsupportedIntent().toJson(),
                                response.toJson())
                    }
                }
                on("FallbackIntent Intent with more than one @FallbackIntent annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.FallbackIntent"
                    val intentExecutor = mockk<KClass<out IntentExecutor>>()
                    val intentExecutor2 = mockk<KClass<out IntentExecutor>>()
                    every { Util.findAnnotatedClasses(any(), FallbackIntent::class) } returns listOf(intentExecutor, intentExecutor2)
                    it("should throw illegal annotation argument") {
                        assertFailsWith(exceptionClass = IllegalAnnotationException::class) {
                            defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        }
                    }
                }
            }
            context("Amazon Help Intent") {
                beforeGroup {
                    every { Util.findAnnotatedClasses(any(), HelpIntent::class) } returns listOf(fakeIntent::class)
                }
                on("Intent with @HelpIntent annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.HelpIntent"
                    it("should call FakeIntent onHelpIntent method") {
                        val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a help response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                                response.toJson())
                    }
                }
                on("Intent without @HelpIntent annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.HelpIntent"
                    every { Util.loadIntentClassesFromPackage() } returns emptyList()
                    every { Util.findAnnotatedClasses(any(), HelpIntent::class) } returns emptyList()
                    it("should call default helpIntent method") {
                        val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(IntentUtil.helpIntent().toJson(),
                                response.toJson())
                    }
                }
                on("Intents with more than one @HelpIntent annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.HelpIntent"
                    val intentExecutor = mockk<KClass<out IntentExecutor>>()
                    val intentExecutor2 = mockk<KClass<out IntentExecutor>>()
                    every { Util.findAnnotatedClasses(any(), HelpIntent::class) } returns listOf(intentExecutor, intentExecutor2)
                    it("should throw illegal annotation argument") {
                        assertFailsWith(exceptionClass = IllegalAnnotationException::class) {
                            defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        }
                    }
                }
            }
            context("On Built In Intent") {
                on("Yes Built in Intent with intent context locked") {
                    every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                    attributesSession[SpeechHandler.INTENT_CONTEXT] = "FakeIntent"
                    it("should call onBuiltInIntent method") {
                        val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"I'm sorry, I couldn't understand what you have said. Could you say it again?"},"directives":[],"shouldEndSession":false},"sessionAttributes":{"com.hp.kalexa.intentContext":"FakeIntent","retry":1},"version":"1.0"}""",
                                response.toJson())
                    }
                }
                on("Unknown Intent Context locked to Yes Built in intent") {
                    attributesSession[SpeechHandler.INTENT_CONTEXT] = "UnknownIntent"
                    every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                    it("should throw IllegalArgumentException") {
                        assertFailsWith(exceptionClass = IllegalArgumentException::class) {
                            defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        }
                    }
                }
                context("Yes Built in Intent without context locked") {
                    on("Intent with @RecoverIntentContext annotation") {
                        every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                        it("should should call unknownIntentContext") {
                            val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                            assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a unknown intent context response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                                    response.toJson())
                        }
                    }
                    on("Intent without @RecoverIntentContext annotation") {
                        every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                        every { Util.loadIntentClassesFromPackage() } returns emptyList()
                        it("should should call default unknownIntentContext response") {
                            val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                            assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"I'm sorry, I couldn't understand what you have said. Could you say it again?"},"directives":[],"shouldEndSession":false},"sessionAttributes":{"retry":1},"version":"1.0"}""",
                                    response.toJson())
                        }
                    }
                    on("Intent with more than one @RecoverIntentContext annotation") {
                        every { intentRequestEnvelope.request.intent.name } returns BuiltInIntent.YES_INTENT.rawValue
                        every { Util.loadIntentClassesFromPackage() } returns emptyList()
                        val intentExecutor = mockk<KClass<out IntentExecutor>>()
                        val intentExecutor2 = mockk<KClass<out IntentExecutor>>()
                        every { Util.findAnnotatedClasses(any(), RecoverIntentContext::class) } returns listOf(intentExecutor, intentExecutor2)
                        it("should throw illegal annotation argument") {
                            assertFailsWith(exceptionClass = IllegalAnnotationException::class) {
                                defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                            }
                        }
                    }
                }
            }
        }
        describe("When handleElementSelectedRequest method is called") {
            val elementSelectedRequest = mockk<AlexaRequestEnvelope<ElementSelectedRequest>>()
            lateinit var fakeIntent: IntentExecutor
            lateinit var context: Context
            lateinit var request: ElementSelectedRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any?>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadIntentClassesFromPackage() } returns listOf(fakeIntent::class)
                every { Util.getIntentPackage() } returns "package.with.intent"
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
                val response = defaultSpeechHandler.handleElementSelectedRequest(elementSelectedRequest)
                it("should use value of Intent Context to call FakeIntent.onElementSelected and return an empty response") {
                    assertEquals("{\"response\":{\"directives\":[]},\"sessionAttributes\":{\"com.hp.kalexa.intentContext\":\"FakeIntent\"},\"version\":\"1.0\"}", response.toJson())
                }
            }
            on("Element Selected with intent context locked and map to an unknown intent") {
                attributesSession[SpeechHandler.INTENT_CONTEXT] = "UnknownIntent"
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) {
                        defaultSpeechHandler.handleElementSelectedRequest(elementSelectedRequest)
                    }
                }
            }
            on("Element Selected with intent context UNLOCKED") {
                val response = defaultSpeechHandler.handleElementSelectedRequest(elementSelectedRequest)
                it("should use value of token to call FakeIntent.onElementSelected and return an empty response") {
                    assertEquals("{\"response\":{\"directives\":[]},\"sessionAttributes\":{},\"version\":\"1.0\"}", response.toJson())
                }
            }
        }
        describe("When handleConnectionsResponseRequest method is called") {
            val connectionsResponseRequest = mockk<AlexaRequestEnvelope<ConnectionsResponseRequest>>()
            lateinit var fakeIntent: IntentExecutor
            lateinit var context: Context
            lateinit var request: ConnectionsResponseRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any?>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadIntentClassesFromPackage() } returns listOf(fakeIntent::class)
                every { Util.getIntentPackage() } returns "package.with.intent"
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
            on("Connections Response ") {
                val response = defaultSpeechHandler.handleConnectionsResponseRequest(connectionsResponseRequest)
                it("should use value of token to call FakeIntent.onConnectionsResponse and return an empty response") {
                    assertEquals("{\"response\":{\"directives\":[]},\"sessionAttributes\":{},\"version\":\"1.0\"}", response.toJson())
                }
            }
            on("Connections response is mapped to an unknown intent") {
                every { request.token } returns "UnknownIntent"
                it("should throw IllegalArgumentException") {
                    assertFailsWith(exceptionClass = IllegalArgumentException::class) {
                        defaultSpeechHandler.handleConnectionsResponseRequest(connectionsResponseRequest)
                    }
                }
            }
        }

        describe("When handleConnectionsRequest method is called") {
            val connectionsRequest = mockk<AlexaRequestEnvelope<ConnectionsRequest>>()
            lateinit var fakeIntent: IntentExecutor
            lateinit var context: Context
            lateinit var request: ConnectionsRequest
            lateinit var session: Session
            val attributesSession = mutableMapOf<String, Any?>()
            beforeEachTest {
                fakeIntent = mockk<FakeIntent>()
                attributesSession.clear()
                every { Util.loadIntentClassesFromPackage() } returns listOf(fakeIntent::class)
                every { Util.getIntentPackage() } returns "package.with.intent"
                session = mockk {
                    every { attributes } returns attributesSession
                }
                context = mockk()
                request = mockk()
                every { connectionsRequest.session } returns session
                every { connectionsRequest.request } returns request
                every { connectionsRequest.context } returns context
                every { connectionsRequest.version } returns "1.0"
                every { Util.findAnnotatedClasses(any(), FulfillerIntent::class) } returns listOf(fakeIntent::class)
            }

            on("Intent without FulfillerIntent annotation") {
                every { Util.loadIntentClassesFromPackage() } returns emptyList()
                every { Util.findAnnotatedClasses(any(), FulfillerIntent::class) } returns emptyList()
                it("should return a default FulfillerIntent response") {
                    every { Util.getIntentPackage() } returns "package.with.no.intent"
                    val alexaResponse = defaultSpeechHandler.handleConnectionsRequest(connectionsRequest)
                    assertEquals(IntentUtil.unsupportedIntent().toJson(), alexaResponse.toJson())
                }
            }
            on("Intent with FulfillerIntent annotation") {
                every { Util.getIntentPackage() } returns "package.with.intent"
                every { Util.loadIntentClassesFromPackage() } returns listOf(fakeIntent::class)

                it("should return a call onConnectionsRequest method from the intent annotated with FulfillerIntent") {
                    val alexaResponse = defaultSpeechHandler.handleConnectionsRequest(connectionsRequest)
                    assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a onConnectionsRequest from FakeIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                            alexaResponse.toJson())
                }
            }
            on("More than one Intent with @FulfillerIntent annotation") {
                val intentExecutor = mockk<KClass<out IntentExecutor>>()
                val intentExecutor2 = mockk<KClass<out IntentExecutor>>()
                every { Util.findAnnotatedClasses(any(), FulfillerIntent::class) } returns listOf(intentExecutor, intentExecutor2)
                it("should throw illegal annotation argument") {
                    assertFailsWith(exceptionClass = IllegalAnnotationException::class) { defaultSpeechHandler.handleConnectionsRequest(connectionsRequest) }
                }
            }
        }
    }
})