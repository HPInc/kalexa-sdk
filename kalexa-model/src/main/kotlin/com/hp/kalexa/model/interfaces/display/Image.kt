package com.hp.kalexa.model.interfaces.display

data class Image(val contentDescription: String = "",
                 val sources: List<ImageInstance> = emptyList())