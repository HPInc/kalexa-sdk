/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.gameengine

data class ProgressRecognizer(
    var recognizer: String? = null,
    var completion: Double? = null
) : Recognizer()
