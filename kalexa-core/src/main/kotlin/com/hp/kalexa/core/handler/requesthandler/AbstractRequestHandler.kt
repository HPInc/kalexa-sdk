/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.handler.requesthandler

import com.hp.kalexa.core.extension.hasSuperClassOf
import com.hp.kalexa.core.handler.BaseHandlerRepository
import com.hp.kalexa.core.intent.BaseHandler
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.response.AlexaResponse
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class AbstractRequestHandler(val repository: BaseHandlerRepository) {

    /**
     * Generates the Alexa response based on the INTENT_CONTEXT. If INTENT_CONTEXT is enabled,
     * then it will add the intent context value (which is the class name of the current intent handler)
     * to the session attributes.
     */
    fun generateResponse(
        intentHandler: BaseHandler,
        request: AlexaRequest<*>,
        alexaResponse: AlexaResponse
    ): AlexaResponse {
        return if (intentHandler.isIntentContextLocked(request) &&
            alexaResponse.sessionAttributes[BasicHandler.INTENT_CONTEXT] == null
        ) {
            alexaResponse.copy(
                sessionAttributes = alexaResponse.sessionAttributes + Pair(
                    BasicHandler.INTENT_CONTEXT,
                    intentHandler::class.java.simpleName
                )
            )
        } else {
            alexaResponse
        }
    }

    /**
     * Looks for the Handler instance in handler instances map using the given class name as key,
     * if no such instance exists with the given key,
     * then it looks for the instance again in the handler instances map by iterating over the values
     * and checking if each value has a super class of the given class.
     * And still if no such element is found and the given class implements some Handler or has a super class of
     * the given class, then a new instance is created and added to the Handler Instances map and the instance is
     * returned.
     * @param clazz of the wanted handler instance.
     * @return handler instance.
     */
    fun getHandler(clazz: KClass<out BaseHandler>): BaseHandler? {
        return repository.handlerInstances[clazz.simpleName!!]
            ?: run {
                repository.handlerInstances.values.find { it::class.hasSuperClassOf(clazz) }
                    ?.let { handler ->
                        repository.handlerInstances[handler::class.simpleName!!] = handler
                        handler
                    }
            } ?: run {
                repository.handlerClasses.find { it == clazz || it.hasSuperClassOf(clazz) }
                    ?.createInstance()
                    ?.let { instance ->
                        repository.handlerInstances[instance::class.simpleName!!] = instance
                        instance
                    }
            }
    }

    /**
     * Checks if any of the entry keys from the classes map is equal to the given intent name, if so, it will return
     * a instance of the corresponding intent handler.
     * @param intentName to be retrieved
     * @param classes map containing all the annotated classes
     */
    fun getIntentHandlerOf(
        intentName: String,
        classes: Map<Set<String>, KClass<out BaseHandler>>
    ): BaseHandler? {
        return classes.entries.find {
            it.key.contains(intentName)
        }?.let {
            getIntentHandlerOf(it.value)
        }
    }

    /**
     * Retrieves an instance of a given intentName, if no such instance exists, it will be created, put it into the hash
     * and return it
     * @return an instance of the intentName
     */
    fun getIntentHandlerOf(kclazz: KClass<out BaseHandler>) =
        repository.handlerInstances.getOrPut(kclazz.simpleName!!) { kclazz.createInstance() }
}
