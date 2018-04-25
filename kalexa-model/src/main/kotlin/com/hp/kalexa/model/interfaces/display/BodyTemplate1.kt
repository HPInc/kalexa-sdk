package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("BodyTemplate1")
data class BodyTemplate1(var backgroundImage: Image? = null,
                         var title: String = "",
                         var textContent: TextContent? = null) : Template()