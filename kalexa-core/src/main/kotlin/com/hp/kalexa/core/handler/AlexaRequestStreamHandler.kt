package com.hp.kalexa.core.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.io.OutputStream


open class AlexaRequestStreamHandler(private val speechHandler: SpeechHandler = DefaultSpeechHandler()) : RequestStreamHandler {
    var speechRequestHandler: SpeechRequestHandler = SpeechRequestHandler(speechHandler)

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val inputBytes = IOUtils.toByteArray(input)
        val inputString = String(inputBytes)
        println(">>>>>>> Incoming json: $inputString")

        val response = speechRequestHandler.process(inputBytes)
        println("<<<<<< Outgoing json $response")
        output.write(response.toByteArray())
    }
}