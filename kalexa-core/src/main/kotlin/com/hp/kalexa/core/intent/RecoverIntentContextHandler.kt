/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

import com.hp.kalexa.model.response.AlexaResponse

interface RecoverIntentContextHandler : BaseHandler {

    /**
     * Handles Built in Intent that has no INTENT_CONTEXT or Intent name attached to the context
     */
    fun onUnknownIntentContext(builtInIntent: BuiltInIntent): AlexaResponse {
        return AlexaResponse.emptyResponse()
    }
}
