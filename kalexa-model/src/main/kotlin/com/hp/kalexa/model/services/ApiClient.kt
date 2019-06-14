/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ApiClient {

    @Throws(IOException::class)
    fun dispatch(uri: String, headers: Map<String, String>, body: String?, method: String): ApiClientResponse {
        val connection = getHttpConnection(uri)
        try {
            writeHeaders(connection, headers, method)
            if (body != null) {
                writeRequestBody(connection, body)
            }
            return readResponseBody(connection)
        } finally {
            connection.disconnect()
        }
    }

    @Throws(IOException::class)
    private fun getHttpConnection(uri: String): HttpURLConnection {
        return URL(uri).openConnection() as HttpURLConnection
    }

    @Throws(IOException::class)
    private fun writeHeaders(connection: HttpURLConnection, headers: Map<String, String>, method: String) {
        connection.requestMethod = method
        connection.doOutput = true
        headers.forEach { (key, value) -> connection.setRequestProperty(key, value) }
    }

    @Throws(IOException::class)
    private fun writeRequestBody(connection: HttpURLConnection, body: String) {
        connection.outputStream.use { out -> out.write(body.toByteArray()) }
    }

    @Throws(IOException::class)
    private fun readResponseBody(connection: HttpURLConnection): ApiClientResponse {
        val response = connection.inputStream.bufferedReader().use { it.readText() }
        return ApiClientResponse(connection.responseCode, response)
    }

    companion object {
        val HTTP_OK_CODE = 200
        val SUCCESS_CODE_RANGE = 200..299
    }
}
