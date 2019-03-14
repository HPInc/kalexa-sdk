/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.interfaces.display

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
        JsonSubTypes.Type(value = RichText::class, name = "RichText"),
        JsonSubTypes.Type(value = PlainText::class, name = "PlainText"))
abstract class TextField

@JsonTypeName("RichText")
data class RichText(var text: String = "") : TextField()

@JsonTypeName("PlainText")
data class PlainText(var text: String = "") : TextField()