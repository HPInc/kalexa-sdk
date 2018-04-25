package com.hp.kalexa.model.payload.log

import com.fasterxml.jackson.annotation.JsonIgnore
import com.hp.kalexa.model.payload.Payload

class Log<out T : Log.LogType>(private val logType: T) : Payload<T> {

    override fun getType(): T = logType

    override fun getTypeName() = logType.getTypeName()

    companion object {
        inline operator fun <reified T : LogType> invoke(block: T.() -> Unit): Log<T> {
            return Log(T::class.java.newInstance().apply(block))
        }
    }

    interface LogType {
        @JsonIgnore
        fun getTypeName(): String = Log::class.java.name
    }
}
