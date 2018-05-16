package com.hp.kalexa.model.payload.print

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.hp.kalexa.model.payload.Payload

data class Print<out T : Print.PrintType>(private val printType: T) : Payload<T> {

    override val type: String = "Print${printType::class.simpleName}Request"
    override val version: String = "1.0"

    @JsonIgnore
    override fun getTypeName() = printType.getTypeName()

    override fun getObject(): T = printType

    companion object {
        inline operator fun <reified T : PrintType> invoke(block: T.() -> Unit): Print<T> {
            return Print(T::class.java.newInstance().apply(block))
        }
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type")
    @JsonSubTypes(
            JsonSubTypes.Type(value = PDF::class, name = "PDF"),
            JsonSubTypes.Type(value = WebPage::class, name = "WebPage"),
            JsonSubTypes.Type(value = ImagePNG::class, name = "ImagePNG"),
            JsonSubTypes.Type(value = ImageJPEG::class, name = "ImageJPEG")
    )
    abstract class PrintType(var title: String, var description: String, var url: String) {
        @JsonIgnore
        fun getTypeName(): String = Print::class.java.name
    }
}