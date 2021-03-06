/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.model

import com.hp.kalexa.core.annotation.Intent
import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.intent.CanFulfillIntentHandler
import com.hp.kalexa.core.intent.FallbackIntentHandler
import com.hp.kalexa.core.intent.HelpIntentHandler
import com.hp.kalexa.core.intent.IntentHandler
import com.hp.kalexa.core.intent.LaunchRequestHandler
import com.hp.kalexa.core.intent.ListEventsHandler
import com.hp.kalexa.core.intent.RecoverIntentContextHandler
import com.hp.kalexa.core.intent.RequesterHandler
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.CanFulfillIntentRequest
import com.hp.kalexa.model.request.SessionResumedRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest
import com.hp.kalexa.model.request.event.ListCreatedEventRequest
import com.hp.kalexa.model.request.event.ListDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsCreatedEventRequest
import com.hp.kalexa.model.request.event.ListItemsDeletedEventRequest
import com.hp.kalexa.model.request.event.ListItemsUpdatedEventRequest
import com.hp.kalexa.model.request.event.ListUpdatedEventRequest
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.dsl.alexaResponse

class FakeIntent : IntentHandler, LaunchRequestHandler, HelpIntentHandler, FallbackIntentHandler,
    RecoverIntentContextHandler, RequesterHandler, ListEventsHandler, CanFulfillIntentHandler {

    override fun onCanFulfillIntent(alexaRequest: AlexaRequest<CanFulfillIntentRequest>): AlexaResponse {
        return alexaResponse {
            response {
                speech {
                    "This is a hello from FakeIntent@onCanFulfillIntent"
                }
            }
        }
    }

    override fun onLaunchIntent(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse {
        return alexaResponse {
            response {
                speech {
                    "This is a hello from FakeIntent@onLaunchIntent"
                }
            }
        }
    }
    @Intent(mapsTo = ["FirstIntent", "SecondIntent", "ThirdIntent"])
    override fun onIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return alexaResponse {
            response {
                speech {
                    "This is a hello from FakeIntent"
                }
                simpleCard {
                    title = "Hello world"
                    content = "This is a content coming from FakeIntent"
                }
            }
        }
    }

    override fun onHelpIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return alexaResponse { response { speech { "This is a help response" } } }
    }

    override fun onFallbackIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        return alexaResponse { response { speech { "This is a fallback response" } } }
    }

    override fun onUnknownIntentContext(builtInIntent: BuiltInIntent): AlexaResponse {
        return alexaResponse { response { speech { "This is a unknown intent context response" } } }
    }

    override fun onSessionResumedRequest(alexaRequest: AlexaRequest<SessionResumedRequest>): AlexaResponse {
        return alexaResponse {
            response {
                speech { "This is a onConnectionsResponse from FakeIntent" }
            }
        }
    }

    override fun onListItemsUpdatedEventRequest(alexaRequest: AlexaRequest<ListItemsUpdatedEventRequest>): AlexaResponse {
        return alexaResponse { response { speech { "This is a ListItemsUpdatedEventRequest response" } } }
    }

    override fun onListItemsDeletedEventRequest(alexaRequest: AlexaRequest<ListItemsDeletedEventRequest>): AlexaResponse {
        return alexaResponse { response { speech { "This is a ListItemsDeletedEventRequest response" } } }
    }

    override fun onListItemsCreatedEventRequest(alexaRequest: AlexaRequest<ListItemsCreatedEventRequest>): AlexaResponse {
        return alexaResponse { response { speech { "This is a ListItemsCreatedEventRequest response" } } }
    }

    override fun onListDeletedEventRequest(alexaRequest: AlexaRequest<ListDeletedEventRequest>): AlexaResponse {
        return alexaResponse { response { speech { "This is a ListDeletedEventRequest response" } } }
    }

    override fun onListUpdatedEventRequest(alexaRequest: AlexaRequest<ListUpdatedEventRequest>): AlexaResponse {
        return alexaResponse { response { speech { "This is a ListUpdatedEventRequest response" } } }
    }

    override fun onListCreatedEventRequest(alexaRequest: AlexaRequest<ListCreatedEventRequest>): AlexaResponse {
        return alexaResponse { response { speech { "This is a ListCreatedEventRequest response" } } }
    }
}