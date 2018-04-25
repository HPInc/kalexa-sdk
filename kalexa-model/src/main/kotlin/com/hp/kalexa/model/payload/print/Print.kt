package com.hp.kalexa.model.payload.print

import com.fasterxml.jackson.annotation.JsonIgnore
import com.hp.kalexa.model.payload.Payload

class Print<out T : Print.PrintType>(private val printType: T) : Payload<T> {

    override fun getTypeName() = printType.getTypeName()

    override fun getType(): T = printType

    companion object {
        inline operator fun <reified T : PrintType> invoke(block: T.() -> Unit): Print<T> {
            return Print(T::class.java.newInstance().apply(block))
        }
    }

    abstract class PrintType(var title: String, var description: String, var url: String) {
        @JsonIgnore
        fun getTypeName(): String = Print::class.java.name
    }
}