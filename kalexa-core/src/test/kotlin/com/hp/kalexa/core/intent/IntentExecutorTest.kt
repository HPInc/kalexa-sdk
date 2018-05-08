package com.hp.kalexa.core.intent

import com.hp.kalexa.core.model.FakeIntent
import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Session
import com.hp.kalexa.model.request.IntentRequest
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

class IntentExecutorTest : Spek({

    given("Fake Intent") {
        val fakeIntent by memoized { FakeIntent() }
        on("onIntentRequest callback method") {
            val session = mockk<Session>()
            val context = mockk<Context>()
            val request = mockk<IntentRequest>()
            val version = "1.0"
            fakeIntent.context = context
            fakeIntent.session = session
            fakeIntent.version = version
            fakeIntent.sessionAttributes = mutableMapOf()
            every { request.intent.name } returns "FakeIntent"
            it("should call onIntentRequest method") {
                val response = fakeIntent.onIntentRequest(request)
                assertEquals("""{"response":{"outputSpeech":{"type":"PlainText","text":"This is a hello from FakeIntent"},"card":{"type":"Simple","title":"Hello world","content":"This is a content coming from FakeIntent"},"directives":[],"shouldEndSession":true},"sessionAttributes":{},"version":"1.0"}"""
                        , response.toJson())
            }
        }
    }
})