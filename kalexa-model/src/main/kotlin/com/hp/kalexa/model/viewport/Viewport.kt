package com.hp.kalexa.model.viewport

import com.fasterxml.jackson.annotation.JsonProperty

data class Viewport(
    @JsonProperty("currentPixelHeight")
    val currentPixelHeight: Int = 0,
    @JsonProperty("currentPixelWidth")
    val currentPixelWidth: Int = 0,
    @JsonProperty("dpi")
    val dpi: Int = 0,
    @JsonProperty("experiences")
    val experiences: List<Experience> = emptyList(),
    @JsonProperty("keyboard")
    val keyboard: List<Keyboard> = emptyList(),
    @JsonProperty("pixelHeight")
    val pixelHeight: Int = 0,
    @JsonProperty("pixelWidth")
    val pixelWidth: Int = 0,
    @JsonProperty("shape")
    val shape: Shape = Shape.RECTANGLE,
    @JsonProperty("touch")
    val touch: List<Touch> = emptyList()
)
