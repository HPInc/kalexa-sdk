package com.hp.kalexa.model.services

/**
 * Defines a general exception that a `DirectiveService` can throw when encountering difficulty while
 * sending a service.
 *
 */
class ServiceException : Exception {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}
