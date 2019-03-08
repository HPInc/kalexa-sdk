package com.hp.kalexa.model.exception

import java.lang.RuntimeException

class IllegalAnnotationException(message: String = "", cause: Throwable? = null): RuntimeException(message, cause)