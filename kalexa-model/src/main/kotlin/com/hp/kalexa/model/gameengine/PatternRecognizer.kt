/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

data class PatternRecognizer(
    var anchor: PatternRecognizerAnchorType? = null,
    var fuzzy: Boolean? = null,
    var gadgetIds: List<String> = emptyList(),
    var actions: List<String> = emptyList(),
    var pattern: List<Pattern> = emptyList()
) : Recognizer()
