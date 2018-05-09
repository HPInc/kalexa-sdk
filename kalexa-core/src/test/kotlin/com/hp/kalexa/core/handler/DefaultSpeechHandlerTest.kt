package com.hp.kalexa.core.handler

import com.hp.kalexa.core.annotation.Fallback
import com.hp.kalexa.core.annotation.Launcher
import com.hp.kalexa.core.intent.IntentExecutor
import com.hp.kalexa.core.model.FakeIntent
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Session
import com.hp.kalexa.model.request.AlexaRequestEnvelope
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.SessionStartedRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.sun.xml.internal.txw2.IllegalAnnotationException
import io.mockk.every
import io.mockk.mockk
import io.mockk.objectMockk
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
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
            on("Intent without Launcher annotation") {
                it("should return a default Launch response") {
                    every { Util.getIntentPackage() } returns "package.with.no.intent"
                    val alexaResponse = defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope)
                    assertEquals(IntentUtil.defaultGreetings().toJson(), alexaResponse.toJson())
                }
            }
            on("Intent with Launcher annotation") {
                every { Util.getIntentPackage() } returns "package.with.intent"
                val fakeIntent = mockk<FakeIntent>()
                every { Util.loadIntentClassesFromPackage() } returns listOf(fakeIntent::class)
                every { Util.findAnnotatedMethod(any(), Launcher::class, any()) } returns mapOf("FakeIntent" to fakeIntent::class)

                it("should return a call onLaunchIntent method from the intent annotated with launcher") {
                    val alexaResponse = defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope)
                    assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a hello from FakeIntent@onLaunchIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                            alexaResponse.toJson())
                }
            }
            on("More than one Intent with @Launcher annotation") {
                val intentExecutor = mockk<KClass<out IntentExecutor>>()
                val intentExecutor2 = mockk<KClass<out IntentExecutor>>()
                every { Util.findAnnotatedMethod(any(), Launcher::class, any()) } returns mutableMapOf("intent1" to intentExecutor, "intent2" to intentExecutor2)
                it("should throw illegal annotation argument") {
                    assertFailsWith(exceptionClass = IllegalAnnotationException::class) { defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope) }
                }
            }
        }
        describe("When handleIntentRequest method is called") {
            val intentRequestEnvelope = mockk<AlexaRequestEnvelope<IntentRequest>>()
            lateinit var fakeIntent: IntentExecutor
            beforeGroup {
                fakeIntent = mockk<FakeIntent>()
                every { Util.loadIntentClassesFromPackage() } returns listOf(fakeIntent::class)
                every { Util.getIntentPackage() } returns "package.with.intent"
                val session = mockk<Session> {
                    every { attributes } returns mutableMapOf()
                }
                val context = mockk<Context>()
                val request = mockk<IntentRequest>()
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
            context("Amazon Fallback Intent") {
                beforeGroup {
                    every { Util.findAnnotatedMethod(any(), Fallback::class, any()) } returns mapOf("FakeIntent" to fakeIntent::class)
                }
                on("Fallback Intent with @Fallback annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.FallbackIntent"
                    it("should call FakeIntent fallback method") {
                        val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a fallback response"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}""",
                                response.toJson())
                    }
                }
                on("Fallback Intent without @Fallback annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.FallbackIntent"
                    every { Util.loadIntentClassesFromPackage() } returns emptyList()
                    every { Util.findAnnotatedMethod(any(), Fallback::class, any()) } returns mapOf()
                    it("should call default fallback method") {
                        val response = defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        assertEquals(IntentUtil.unsupportedIntent().toJson(),
                                response.toJson())
                    }
                }
                on("Fallback Intent with more than one @Fallback annotation") {
                    every { intentRequestEnvelope.request.intent.name } returns "AMAZON.FallbackIntent"
                    val intentExecutor = mockk<KClass<out IntentExecutor>>()
                    val intentExecutor2 = mockk<KClass<out IntentExecutor>>()
                    every { Util.findAnnotatedMethod(any(), Fallback::class, any()) } returns mutableMapOf("intent1" to intentExecutor, "intent2" to intentExecutor2)
                    it("should throw illegal annotation argument") {
                        assertFailsWith(exceptionClass = IllegalAnnotationException::class) {
                            defaultSpeechHandler.handleIntentRequest(intentRequestEnvelope)
                        }
                    }
                }
            }
        }
    }
})