/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.request

data class SessionEndedError(val type: SessionEndedErrorType? = null, val message: String? = null)