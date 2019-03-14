/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hp.kalexa.model.exception.KalexaSDKException
import com.hp.kalexa.model.request.AlexaRequest
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object JacksonSerializer {

    val OBJECT_MAPPER = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

    fun fromJson(json: String): AlexaRequest<*> {
        return OBJECT_MAPPER.readValue(json, AlexaRequest::class.java)
    }

    fun fromJson(json: ByteArray): AlexaRequest<*> {
        return OBJECT_MAPPER.readValue(json, AlexaRequest::class.java)
    }

    fun <T> serialize(obj: T): String {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj)
        } catch (e: IOException) {
            throw KalexaSDKException("Something went wrong while serializing an object", e)
        }

    }

    fun <T> serialize(`object`: T, outputStream: OutputStream) {
        try {
            OBJECT_MAPPER.writeValue(outputStream, `object`)
        } catch (e: IOException) {
            throw KalexaSDKException("Something went wrong while serializing an object")
        }

    }

    fun <T> deserialize(s: String, aClass: Class<T>): T {
        try {
            return OBJECT_MAPPER.readValue(s, aClass)
        } catch (e: IOException) {
            throw KalexaSDKException("Something went wrong while deserializing an object", e)
        }
    }

    fun <T> deserialize(input: ByteArray, aClass: Class<T>): T {
        try {
            return OBJECT_MAPPER.readValue(input, aClass)
        } catch (e: IOException) {
            throw KalexaSDKException("Something went wrong while deserializing an object", e)
        }
    }

    fun <T> deserialize(inputStream: InputStream, type: Class<T>): T {
        try {
            return OBJECT_MAPPER.readValue(inputStream, type)
        } catch (e: IOException) {
            throw KalexaSDKException("Something went wrong while deserializing an object", e)
        }
    }

    fun <T> convert(any: Any, type: Class<T>): T {
        try {
            return OBJECT_MAPPER.convertValue(any, type)
        } catch (e: IOException) {
            throw KalexaSDKException("Something went wrong while converting to an object", e)
        }
    }
}