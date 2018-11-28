package com.hp.kalexa.model.exception

import java.lang.Exception

class KalexaSDKException(message: String = "", cause: Throwable? = null): Exception(message, cause)