package com.hp.kalexa.model.payload

class EmptyPayload : Payload<EmptyPayload> {

    override fun getType(): EmptyPayload = this

    override fun getTypeName(): String = this.javaClass.name
}