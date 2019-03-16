/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.ui

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
        JsonSubTypes.Type(value = PlainTextOutputSpeech::class, name = "PlainText"),
        JsonSubTypes.Type(value = SsmlOutputSpeech::class, name = "SSML"))
abstract class OutputSpeech

@JsonTypeName("PlainText")
data class PlainTextOutputSpeech(var text: String = "") : OutputSpeech()

@JsonTypeName("SSML")
data class SsmlOutputSpeech(var ssml: String = "") : OutputSpeech()
