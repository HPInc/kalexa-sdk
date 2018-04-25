package com.hp.kalexa.model.interfaces.display

data class ListItem(
        var image: Image? = null,
        var textContent: TextContent? = null,
        var token: String = "") {

    fun textContent(block: TextContent.() -> Unit) {
        textContent = TextContent().apply { block() }
    }
}

