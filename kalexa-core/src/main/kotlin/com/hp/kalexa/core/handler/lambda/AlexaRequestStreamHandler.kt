/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.hp.kalexa.core.handler.DefaultSpeechHandler
import com.hp.kalexa.core.handler.SpeechHandler
import com.hp.kalexa.core.handler.SpeechRequestHandler
import org.apache.commons.io.IOUtils
import org.apache.logging.log4j.LogManager
import java.io.InputStream
import java.io.OutputStream


open class AlexaRequestStreamHandler(speechHandler: SpeechHandler = DefaultSpeechHandler()) : RequestStreamHandler {
    private val logger = LogManager.getLogger(AlexaRequestStreamHandler::class.java)

    var speechRequestHandler: SpeechRequestHandler = SpeechRequestHandler(speechHandler)

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val inputBytes = IOUtils.toByteArray(input)
        val inputString = String(inputBytes)
        logger.info(">>>>>>> Incoming json: $inputString")

        val response = speechRequestHandler.process(inputBytes)
        logger.info("<<<<<< Outgoing json $response")
        output.write(response.toByteArray())
    }
}