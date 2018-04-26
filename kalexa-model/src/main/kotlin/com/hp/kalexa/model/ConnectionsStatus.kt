package com.hp.kalexa.model

data class ConnectionsStatus(val code: String, val message: String) {

    fun isSuccess() = code == "200"

}