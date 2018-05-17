package com.hp.kalexa.model.payload.log

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.hp.kalexa.model.payload.Payload

class Log<out T : Log.LogType>(private val logType: T) : Payload<T> {

    override val type: String = "Log${logType::class.simpleName}Request"
    override val version: String = "1.0"

    override fun getObject(): T = logType

    @JsonIgnore
    override fun getTypeName() = logType.getTypeName()

    companion object {
        inline operator fun <reified T : LogType> invoke(block: T.() -> Unit): Log<T> {
            return Log(T::class.java.newInstance().apply(block))
        }
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type")
    @JsonSubTypes(
            JsonSubTypes.Type(value = PhysicalActivity::class, name = "PhysicalActivity")
    )
    abstract class LogType(var version: String = "1.0") {
        @JsonIgnore
        fun getTypeName(): String = Log::class.java.name
    }
}
