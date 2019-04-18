/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler

import com.amazonaws.services.lambda.runtime.Context
import com.hp.kalexa.core.handler.lambda.AlexaRequestStreamHandler
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

object AlexaRequestStreamHandlerTest : Spek({
    describe("Alexa Request Stream Handler") {
        on("handle request method") {
            val expectedMsg = "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
            val speechRequestHandler: SpeechRequestHandler = mockk<SpeechRequestHandler> {
                every { process(any()) } returns expectedMsg
            }
            val skillConfig = mockk<SkillConfig>()
            val alexaRequestStreamHandler by memoized {
                val alexaRequestStreamHandler = AlexaRequestStreamHandler(skillConfig, speechRequestHandler)
                alexaRequestStreamHandler
            }
            val inputStream = ByteArrayInputStream("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.".toByteArray())
            val outputStream = ByteArrayOutputStream()
            val context = mockk<Context>()
            it("should return the mocked text") {
                alexaRequestStreamHandler.handleRequest(inputStream, outputStream, context)
                assertEquals(expectedMsg, outputStream.toString())
            }
        }
    }
})