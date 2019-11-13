/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    Type(value = ProgressRecognizer::class, name = "ProgressRecognizer"),
    Type(value = PatternRecognizer::class, name = "PatternRecognizer"),
    Type(value = DeviationRecognizer::class, name = "DeviationRecognizer")
)
abstract class Recognizer
