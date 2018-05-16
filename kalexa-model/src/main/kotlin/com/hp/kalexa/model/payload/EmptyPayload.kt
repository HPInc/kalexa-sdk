package com.hp.kalexa.model.payload

class EmptyPayload : Payload<EmptyPayload> {
    override val type: String = "EmptyRequest"
    override val version: String = "1.0"

    override fun getObject(): EmptyPayload = this

    override fun getTypeName(): String = this.javaClass.name
}