package com.hp.kalexa.core.intent

import com.hp.kalexa.core.handler.SpeechHandler
import com.hp.kalexa.core.model.DummyIntent
import com.hp.kalexa.model.*
import com.hp.kalexa.model.interfaces.display.Display
import com.hp.kalexa.model.request.IntentRequest
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IntentExecutorTest : Spek({

    given("Intent Executor implementor") {
        val dummyIntent by memoized { DummyIntent() }
        val session = mockk<Session>()
        val context = mockk<Context>()
        val request = mockk<IntentRequest>()
        val version = "1.0"
        beforeEachTest {
            dummyIntent.context = context
            dummyIntent.session = session
            dummyIntent.version = version
            dummyIntent.sessionAttributes = mutableMapOf()
            every { request.intent.name } returns "DummyIntent"
        }
        on("getSkillName method") {
            it("should return the skill name") {
                val response = dummyIntent.getSkillName()
                assertEquals("This Skill", response)
            }
        }

        on("Launch Intent") {
            val response = dummyIntent.onLaunchIntent(mockk())
            it("should return default response") {
                assertEquals("{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"Hello, welcome to This Skill. What can I do for you?\"},\"card\":{\"type\":\"Simple\",\"title\":\"This Skill\",\"content\":\"Hello, welcome to This Skill. What can I do for you?\"},\"directives\":[],\"shouldEndSession\":false},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())
            }
        }

        on("onIntentRequest") {
            it("should return default response") {
                val response = dummyIntent.onIntentRequest(request)
                assertEquals("{\"response\":{\"directives\":[]},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())
            }
        }

        on("onConnectionsResponse") {
            it("should return default response") {
                val response = dummyIntent.onConnectionsResponse(mockk())
                assertEquals("{\"response\":{\"directives\":[]},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())
            }
        }

        on("BuiltInIntent") {
            it("should call retryIntent default response") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.FALLBACK_INTENT, mockk())
                assertEquals("{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"I'm sorry, I couldn't understand what you have said. Could you say it again?\"},\"directives\":[],\"shouldEndSession\":false},\"sessionAttributes\":{\"retry\":1},\"version\":\"1.0\"}",
                        response.toJson())
            }
            it("should call onYesIntent implemented callback through onBuiltInIntent ") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.YES_INTENT, mockk())
                assertEquals("{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"Yes Intent\"},\"directives\":[],\"shouldEndSession\":true},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())

            }
            it("should call onNoIntent implemented callback through onBuiltInIntent ") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.NO_INTENT, mockk())
                assertEquals("{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"No Intent\"},\"directives\":[],\"shouldEndSession\":true},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())

            }
            it("should call onStopIntent implemented callback through onBuiltInIntent ") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.STOP_INTENT, mockk())
                assertEquals("{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"Stop Intent\"},\"directives\":[],\"shouldEndSession\":true},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())

            }
            it("should call onHelpIntent implemented callback through onBuiltInIntent ") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.HELP_INTENT, mockk())
                assertEquals("{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"Help Intent\"},\"directives\":[],\"shouldEndSession\":true},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())

            }
            it("should call onCancelIntent implemented callback through onBuiltInIntent ") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.CANCEL_INTENT, mockk())
                assertEquals("{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"Cancel Intent\"},\"directives\":[],\"shouldEndSession\":true},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())

            }
        }

        on("onElementSelected") {
            it("should return default response") {
                val response = dummyIntent.onElementSelected(mockk())
                assertEquals("{\"response\":{\"directives\":[]},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())
            }
        }

        on("onUnknownIntentContext") {
            it("should return default response") {
                val response = dummyIntent.onUnknownIntentContext(mockk())
                assertEquals("{\"response\":{\"directives\":[]},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())
            }
        }

        on("onFallbackIntent") {
            it("should return default response") {
                val response = dummyIntent.onFallbackIntent(mockk())
                assertEquals("{\"response\":{\"outputSpeech\":{\"type\":\"PlainText\",\"text\":\"This is unsupported. Please try something else.\"},\"card\":{\"type\":\"Simple\",\"title\":\"Unsupported Intent\",\"content\":\"This is unsupported. Please try something else.\"},\"directives\":[],\"shouldEndSession\":true},\"sessionAttributes\":{},\"version\":\"1.0\"}",
                        response.toJson())
            }
        }

        on("lockIntentContext") {
            it("should return true") {
                dummyIntent.lockIntentContext()
                assertEquals(dummyIntent.sessionAttributes[SpeechHandler.INTENT_CONTEXT], "DummyIntent")
            }
        }

        on("unlockIntentContext") {
            it("should return default response") {
                dummyIntent.unlockIntentContext()
                assertNull(dummyIntent.sessionAttributes[SpeechHandler.INTENT_CONTEXT])
            }
        }

        on("isIntentContextLocked") {
            it("should return true") {
                dummyIntent.lockIntentContext()
                val response = dummyIntent.isIntentContextLocked()
                assertTrue { response }
            }
            it("should return false") {
                dummyIntent.unlockIntentContext()
                val response = dummyIntent.isIntentContextLocked()
                assertFalse { response }
            }
        }
        on("hasDisplay") {
            val system = mockk<System>()
            val device = mockk<Device>()
            val supportedInterfaces = mockk<SupportedInterfaces>()
            val display = mockk<Display>()
            every { context.system } returns system
            every { system.device } returns device
            every { device.supportedInterfaces } returns supportedInterfaces
            every { supportedInterfaces.display } returns display
            it("should have display interface") {
                every {display.markupVersion} returns "1.0"
                every {display.templateVersion} returns "1.0"
                val response = dummyIntent.hasDisplay()
                assertTrue { response }
            }
            it("should not have display interface") {
                every {display.markupVersion} returns ""
                every {display.templateVersion} returns ""
                val response = dummyIntent.hasDisplay()
                assertFalse { response }
            }
        }
    }
})