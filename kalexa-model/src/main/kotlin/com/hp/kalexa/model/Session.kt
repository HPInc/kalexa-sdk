package com.hp.kalexa.model

data class Session(val new: Boolean? = null,
                   val sessionId: String? = null,
                   val user: User? = null,
                   val attributes: MutableMap<String, Any> = mutableMapOf(),
                   val application: Application? = null)
