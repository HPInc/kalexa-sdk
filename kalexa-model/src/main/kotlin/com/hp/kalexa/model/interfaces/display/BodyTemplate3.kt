package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("BodyTemplate3")
data class BodyTemplate3(var backgroundImage: Image? = null,
                         var image: Image? = null,
                         var title: String = "",
                         var textContent: TextContent? = null) : Template()
