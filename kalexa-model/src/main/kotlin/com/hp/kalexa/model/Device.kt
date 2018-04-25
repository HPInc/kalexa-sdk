package com.hp.kalexa.model

data class Device(val deviceId: String,
                  val supportedInterfaces: SupportedInterfaces? = null)