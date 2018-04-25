package com.hp.kalexa.model.payload

interface Payload<out T> {

    fun getType(): T

    fun getTypeName(): String
}