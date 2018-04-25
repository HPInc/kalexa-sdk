package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
        JsonSubTypes.Type(value = ListTemplate2::class, name = "ListTemplate2"),
        JsonSubTypes.Type(value = ListTemplate1::class, name = "ListTemplate1"),
        JsonSubTypes.Type(value = BodyTemplate7::class, name = "BodyTemplate7"),
        JsonSubTypes.Type(value = BodyTemplate6::class, name = "BodyTemplate6"),
        JsonSubTypes.Type(value = BodyTemplate3::class, name = "BodyTemplate3"),
        JsonSubTypes.Type(value = BodyTemplate2::class, name = "BodyTemplate2"),
        JsonSubTypes.Type(value = BodyTemplate1::class, name = "BodyTemplate1"))
abstract class Template(var token: String? = null,
                        var backButton: BackButtonBehavior? = null)