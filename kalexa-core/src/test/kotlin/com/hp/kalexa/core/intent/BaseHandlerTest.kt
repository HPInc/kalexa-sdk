/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.core.handler.requesthandler.BasicHandler
import com.hp.kalexa.core.model.DummyIntent
import com.hp.kalexa.core.util.IntentUtil
import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Device
import com.hp.kalexa.model.Session
import com.hp.kalexa.model.SupportedInterfaces
import com.hp.kalexa.model.System
import com.hp.kalexa.model.interfaces.display.Display
import com.hp.kalexa.model.request.AlexaRequest
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

class BaseHandlerTest : Spek({

    given("Intent Handler implementor") {
        val dummyIntent: BaseHandler by memoized { DummyIntent() }
        val attributes = mutableMapOf<String, Any>()
        val session = mockk<Session>()
        val context = mockk<Context>()
        val envelope = mockk<AlexaRequest<IntentRequest>>()

        beforeEachTest {
            every { session.attributes } returns attributes
            every { envelope.session } returns session
            every { envelope.context } returns context
            every { envelope.request.intent.name } returns "DummyIntent"
        }
        on("getSkillName method") {
            it("should return the skill name") {
                val response = dummyIntent.getSkillName()
                assertEquals("This Skill", response)
            }
        }

        on("BuiltInIntent") {
            it("should call retryIntent default response with repeat message") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.FALLBACK_INTENT, envelope)
                assertEquals(IntentUtil.retryIntent(mutableMapOf()).toJson(),
                        response.toJson())
            }
            it("should call onYesIntent retryIntent default response with end message.") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.YES_INTENT, envelope)
                assertEquals(IntentUtil.retryIntent(mutableMapOf("retry" to 1)).toJson(),
                        response.toJson())
            }
            it("should call onNoIntent default response callback through onBuiltInIntent ") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.NO_INTENT, envelope)
                assertEquals(IntentUtil.finish().toJson(),
                        response.toJson())
            }
            it("should call onStopIntent default response callback through onBuiltInIntent ") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.STOP_INTENT, envelope)
                assertEquals(IntentUtil.goodbye().toJson(),
                        response.toJson())
            }
            it("should call onCancelIntent default response callback through onBuiltInIntent ") {
                val response = dummyIntent.onBuiltInIntent(BuiltInIntent.CANCEL_INTENT, envelope)
                assertEquals(IntentUtil.finish().toJson(),
                        response.toJson())
            }
        }
        on("No Intent") {
            val response = dummyIntent.onNoIntent(envelope)
            it("should return default response") {
                assertEquals(IntentUtil.finish().toJson(),
                        response.toJson())
            }
        }
        on("Yes Intent") {
            every { envelope.session?.attributes } returns mutableMapOf()
            val response = dummyIntent.onYesIntent(envelope)
            it("should return default response") {
                assertEquals(IntentUtil.retryIntent(mutableMapOf()).toJson(),
                        response.toJson())
            }
        }
        on("Stop Intent") {
            val response = dummyIntent.onStopIntent(envelope)
            it("should return default response") {
                assertEquals(IntentUtil.goodbye().toJson(),
                        response.toJson())
            }
        }
        on("Cancel Intent") {
            val response = dummyIntent.onCancelIntent(envelope)
            it("should return default response") {
                assertEquals(IntentUtil.finish().toJson(),
                        response.toJson())
            }
        }

        on("onElementSelected") {
            it("should return default response") {
                val response = dummyIntent.onElementSelected(mockk())
                assertEquals("""{"response":{},"version":"1.0"}""",
                        response.toJson())
            }
        }

        on("lockIntentContext") {
            it("should return true") {
                val response = mutableMapOf<String, Any>()
                every { envelope.session } returns session
                every { envelope.session?.attributes } returns response
                dummyIntent.lockIntentContext(envelope)
                assertEquals(response[BasicHandler.INTENT_CONTEXT], "DummyIntent")
            }
        }

        on("unlockIntentContext") {
            it("should return default response") {
                val response = mutableMapOf<String, Any>()
                every { envelope.session } returns session
                every { envelope.session?.attributes } returns response
                dummyIntent.unlockIntentContext(envelope)
                assertNull(response[BasicHandler.INTENT_CONTEXT])
            }
        }

        on("isIntentContextLocked") {
            it("should return true") {
                val response = mutableMapOf<String, Any>()
                every { envelope.session } returns session
                every { envelope.session?.attributes } returns response
                dummyIntent.lockIntentContext(envelope)
                val isIntentContextLocked = dummyIntent.isIntentContextLocked(envelope)
                assertTrue { isIntentContextLocked }
            }
            it("should return false") {
                val response = mutableMapOf<String, Any>()
                every { envelope.session } returns session
                every { envelope.session?.attributes } returns response
                dummyIntent.unlockIntentContext(envelope)
                val isIntentContextLocked = dummyIntent.isIntentContextLocked(envelope)
                assertFalse { isIntentContextLocked }
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
                every { context.hasDisplay() } returns true
                val response = dummyIntent.hasDisplay(envelope)
                assertTrue { response }
            }
            it("should not have display interface") {
                every { context.hasDisplay() } returns false
                val response = dummyIntent.hasDisplay(envelope)
                assertFalse { response }
            }
        }
    }
})
