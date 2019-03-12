package com.hp.kalexa.core.handler.webapplication

import com.hp.kalexa.core.handler.DefaultSpeechHandler
import com.hp.kalexa.core.handler.SpeechHandler
import com.hp.kalexa.core.handler.SpeechRequestHandler
import org.apache.logging.log4j.LogManager

class AlexaWebApplication(speechHandler: SpeechHandler = DefaultSpeechHandler()) {
    private val logger = LogManager.getLogger(AlexaWebApplication::class.java)

    var speechRequestHandler: SpeechRequestHandler = SpeechRequestHandler(speechHandler)

    fun process(input: String): ByteArray {
        return process(input.toByteArray())
    }

    fun process(input: ByteArray): ByteArray {
        val inputString = String(input)
        logger.info(">>>>>>> Incoming json: $inputString")

        val response = speechRequestHandler.process(input)
        logger.info("<<<<<< Outgoing json $response")
        return response.toByteArray()
    }
}