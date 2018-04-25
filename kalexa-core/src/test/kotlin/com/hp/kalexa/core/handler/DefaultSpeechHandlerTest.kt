package com.hp.kalexa.core.handler

import com.hp.kalexa.core.annotation.Launcher
import com.hp.kalexa.core.intent.IntentExecutor
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.core.util.Util
import com.hp.kalexa.model.*
import com.hp.kalexa.model.request.AlexaRequestEnvelope
import com.sun.xml.internal.txw2.IllegalAnnotationException
import io.mockk.every
import io.mockk.mockk
import io.mockk.objectMockk
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DefaultSpeechHandlerTest : Spek({

    given("a Default speech handler class") {
        val defaultSpeechHandler by memoized { DefaultSpeechHandler() }
        val customLaunchRequestEnvelope by memoized { mockk<AlexaRequestEnvelope<LaunchRequest>>() }
        objectMockk(Util).mock()
        every { Util.getIntentPackage() } returnsMany listOf("package.with.no.intent", "package.with.no.intent", "com.hp.kotlinlambda.model")

        on("Start") {
            objectMockk(Util).mock()
            every { Util.getIntentPackage() } returns "com.hp.kalexa.model"
        }

        on("handleSessionStartedRequest method") {
            val envelope = mockk<AlexaRequestEnvelope<SessionStartedRequest>>()
            it("should return an empty response") {
                val alexaResponse = defaultSpeechHandler.handleSessionStartedRequest(envelope)
                assertEquals(AlexaResponse.emptyResponse().toJson(), alexaResponse.toJson())
            }
        }
        context("handleLaunchRequest method") {
            on("Intent without Launcher annotation") {
                it("should return a default Launch response") {
                    objectMockk(Util).mock()
                    every { Util.getIntentPackage() } returns "package.with.no.intent"
                    val alexaResponse = defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope)
                    assertEquals(IntentUtil.defaultGreetings().toJson(), alexaResponse.toJson())
                }
            }
            on("Intent with Launcher annotation") {
                it("should return a call onLaunchIntent method from the intent annotated with launcher") {
                    objectMockk(Util).mock()
                    every { Util.getIntentPackage() } returns "com.hp.kotlinlambda.model"
                    val session = mockk<Session>()
                    val context = mockk<Context>()
                    val request = mockk<LaunchRequest>()
                    val version = "1.0"
                    every { customLaunchRequestEnvelope.session } returns session
                    every { customLaunchRequestEnvelope.context } returns context
                    every { customLaunchRequestEnvelope.version } returns version
                    every { customLaunchRequestEnvelope.request } returns request
                    every { session.attributes } returns mutableMapOf()
                    val alexaResponse = defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope)
                    val response = alexaResponse {
                        response {
                            speech { "This is a hello from IntentFake@onCustomLaucnher" }
                        }
                    }.toJson()
                    assertEquals(response, alexaResponse.toJson())
                }
            }
            on("Intents with more than one @Launcher annotation") {
                val intentExecutor = mockk<KClass<out IntentExecutor>>()
                val intentExecutor2 = mockk<KClass<out IntentExecutor>>()
                it("should throw illegal annotation argument") {
                    objectMockk(Util).mock()
                    every { Util.findAnnotatedMethod(any(), Launcher::class, any()) } returns mutableMapOf("intent1" to intentExecutor, "intent2" to intentExecutor2)
                    every { Util.getIntentPackage() } returns "valid.package.name"
                    assertFailsWith(exceptionClass = IllegalAnnotationException::class) { defaultSpeechHandler.handleLaunchRequest(customLaunchRequestEnvelope) }
                }
            }
        }
    }
})