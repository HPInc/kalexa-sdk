package com.hp.kalexa.model.request


data class SessionEndedError(val type: SessionEndedErrorType? = null, val message: String? = null)