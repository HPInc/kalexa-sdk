package com.hp.kalexa.model.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.hp.kalexa.model.TargetURI

class TargetURIDeserializer : JsonDeserializer<TargetURI>() {

    override fun deserialize(jsonParser: JsonParser, ctxt: DeserializationContext?): TargetURI? {
        val node: JsonNode = jsonParser.codec.readTree(jsonParser)
        val nodeText = node.asText()
        val action = nodeText.split("/").last()
        return try {
            TargetURI.valueOf(action.toUpperCase())
        } catch (e: IllegalArgumentException) {
            return null
        }
    }
}