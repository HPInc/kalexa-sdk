package com.hp.kalexa.model.services

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class ApiClient {

    @Throws(IOException::class)
    fun post(uri: String, headers: Map<String, String>, body: String): ApiClientResponse {
        return dispatch(uri, headers, body, "POST")
    }

    @Throws(IOException::class)
    fun put(uri: String, headers: Map<String, String>, body: String): ApiClientResponse {
        return dispatch(uri, headers, body, "PUT")
    }

    @Throws(IOException::class)
    fun get(uri: String, headers: Map<String, String>): ApiClientResponse {
        return dispatch(uri, headers, null, "GET")
    }

    @Throws(IOException::class)
    fun delete(uri: String, headers: Map<String, String>): ApiClientResponse {
        return dispatch(uri, headers, null, "DELETE")
    }

    @Throws(IOException::class)
    private fun dispatch(uri: String, headers: Map<String, String>, body: String?, method: String): ApiClientResponse {
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
        val url = URL(uri)
        return url.openConnection() as HttpURLConnection
    }

    @Throws(IOException::class)
    private fun writeHeaders(connection: HttpURLConnection, headers: Map<String, String>, method: String) {
        connection.requestMethod = method
        connection.doOutput = true
        headers.forEach { key, value -> connection.setRequestProperty(key, value) }
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
}
