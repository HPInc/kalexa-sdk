package com.hp.kalexa.model

data class User(
        val userId: String,
        val accessToken: String = "",
        val permissions: Permissions? = null

)

