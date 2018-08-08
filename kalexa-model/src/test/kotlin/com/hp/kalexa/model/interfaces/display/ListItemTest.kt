package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hp.kalexa.model.response.plainText
import com.hp.kalexa.model.response.richText
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

class ListItemTest : Spek({
    given("List Item Object") {
        val mapper = jacksonObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

        on("Entire List Item") {
            val list = ListItem()
            list.image = Image("Content descriptor")
            list.token = "Token Value"
            list.textContent {
                primaryText = plainText { "primary" }
                secondaryText = plainText { "secondary" }
                tertiaryText = richText { "tertiary" }
            }
            it("should return a json") {
                assertEquals("{\"image\":{\"contentDescription\":\"Content descriptor\",\"sources\":[]},\"textContent\":{\"primaryText\":{\"type\":\"PlainText\",\"text\":\"primary\"},\"secondaryText\":{\"type\":\"PlainText\",\"text\":\"secondary\"},\"tertiaryText\":{\"type\":\"RichText\",\"text\":\"tertiary\"}},\"token\":\"Token Value\"}",
                        mapper.writeValueAsString(list))
            }
        }
        on("TextContent") {
            val list = ListItem()
            list.textContent {
                primaryText = plainText { "primary" }
                secondaryText = plainText { "secondary" }
                tertiaryText = richText { "tertiary" }
            }
            it("should parse to json") {
                val listItemJson = mapper.writeValueAsString(list.textContent)
                assertEquals("{\"primaryText\":{\"type\":\"PlainText\",\"text\":\"primary\"},\"secondaryText\":{\"type\":\"PlainText\",\"text\":\"secondary\"},\"tertiaryText\":{\"type\":\"RichText\",\"text\":\"tertiary\"}}",
                        listItemJson)
            }
        }
        on("Text Content with rich text primary text only") {
            val list = ListItem()
            list.textContent {
                primaryText = richText { "primary" }
            }
            it("should return json with rich text primary text") {
                val listItemJson = mapper.writeValueAsString(list.textContent)
                assertEquals("{\"primaryText\":{\"type\":\"RichText\",\"text\":\"primary\"}}",
                        listItemJson)
            }
        }
        on("Text Content with plain primary and rich secondary text") {
            val list = ListItem()
            list.textContent {
                primaryText = plainText { "primary" }
                secondaryText = richText { "secondary" }
            }
            it("should return json with rich text primary text") {
                val listItemJson = mapper.writeValueAsString(list.textContent)
                assertEquals("{\"primaryText\":{\"type\":\"PlainText\",\"text\":\"primary\"},\"secondaryText\":{\"type\":\"RichText\",\"text\":\"secondary\"}}",
                        listItemJson)
            }
        }
    }
})