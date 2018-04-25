package com.hp.kalexa.model.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.reflect.ClassPath
import com.hp.kalexa.model.payload.EmptyPayload
import com.hp.kalexa.model.payload.Payload

class PayloadDeserializer : JsonDeserializer<Payload<*>>() {

    override fun deserialize(jsonParser: JsonParser, cxt: DeserializationContext): Payload<*> {
        val node: JsonNode = jsonParser.codec.readTree(jsonParser)
        val topLevelClasses = ClassPath.from(Thread.currentThread().contextClassLoader)
                .getTopLevelClassesRecursive(PAYLOAD_PACKAGE)
        return topLevelClasses.find { node.has(it.simpleName) }
                ?.let {
                    try {
                        val clazz = it.load()
                        val obj = jacksonObjectMapper().readValue(node.get(it.simpleName).toString(), clazz)
                        val getTypeNameMethod = clazz.getMethod(GET_TYPE_METHOD)
                        getTypeNameMethod?.let {
                            val typeName = it.invoke(obj)
                            val typeNameClazz = Class.forName(typeName.toString())
                            val constructor = typeNameClazz.declaredConstructors.first()
                            val newInstance = constructor.newInstance(obj)
                            return newInstance as? Payload<*> ?: EmptyPayload()
                        }
                    } catch (e: Exception) {
                        println("Couldn't instantiate clazz $it")
                        EmptyPayload()
                    } ?: EmptyPayload()
                } ?: EmptyPayload()
    }

    companion object {
        private const val PAYLOAD_PACKAGE = "com.hp.kalexa.model.payload"
        private const val GET_TYPE_METHOD = "getTypeName"
    }
}