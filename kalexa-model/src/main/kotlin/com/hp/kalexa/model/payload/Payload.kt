package com.hp.kalexa.model.payload

interface Payload<out T> {

    val type: String
    val version: String

    fun getObject(): T

    fun getTypeName(): String
}