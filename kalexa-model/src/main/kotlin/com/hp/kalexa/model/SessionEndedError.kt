package com.hp.kalexa.model


data class SessionEndedError(val type: SessionEndedErrorType? = null, val message: String? = null)