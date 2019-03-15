/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.exception

import java.lang.Exception

class KalexaSDKException(message: String = "", cause: Throwable? = null) : Exception(message, cause)