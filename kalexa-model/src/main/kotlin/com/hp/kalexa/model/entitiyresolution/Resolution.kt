package com.hp.kalexa.model.entitiyresolution

data class Resolution(
        val authority: String,
        val status: Status,
        val values: List<ValueWrapper> = emptyList())
