package com.hp.kalexa.model

data class Permissions(
        val consentToken: String? = null,
        val scopes: Map<String, Scope>? = null
)
