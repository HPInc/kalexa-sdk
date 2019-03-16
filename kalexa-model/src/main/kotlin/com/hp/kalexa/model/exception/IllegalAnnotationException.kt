/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.exception

import java.lang.RuntimeException

class IllegalAnnotationException(message: String = "", cause: Throwable? = null) : RuntimeException(message, cause)
