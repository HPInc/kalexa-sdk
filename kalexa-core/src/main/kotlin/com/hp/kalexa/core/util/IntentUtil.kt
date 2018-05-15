package com.hp.kalexa.core.util

import com.hp.kalexa.core.intent.BuiltInIntent
import com.hp.kalexa.core.util.Util.getSkillName
import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Slot
import com.hp.kalexa.model.entitiyresolution.StatusCode
import com.hp.kalexa.model.extension.getAttr
import com.hp.kalexa.model.response.AlexaResponse
import com.hp.kalexa.model.response.alexaResponse

object IntentUtil {

    internal fun defaultBuiltInResponse(builtInIntent: BuiltInIntent, attributes: MutableMap<String, Any?> = mutableMapOf()): AlexaResponse {
        return when (builtInIntent) {
            BuiltInIntent.CANCEL_INTENT -> finish()
            BuiltInIntent.STOP_INTENT -> finish()
            BuiltInIntent.FALLBACK_INTENT -> finish()
            else -> retryIntent(attributes)
        }
    }

    fun defaultGreetings(): AlexaResponse {
        val speechText = "Hello, welcome to ${getSkillName()}. What can I do for you?"
        return alexaResponse {
            response {
                simpleCard {
                    title = getSkillName()
                    content = speechText
                }
                speech {
                    speechText
                }
                shouldEndSession = false
            }
        }
    }

    fun finish(): AlexaResponse = alexaResponse {
        response {
            simpleCard {
                title = "Goodbye"
                content = "All right, have a nice day!"
            }
            speech {
                "All right, have a nice day!"
            }
            shouldEndSession = true
        }
    }

    fun goodbye(): AlexaResponse = alexaResponse {
        response {
            simpleCard {
                title = "Goodbye"
                content = "All right. Goodbye then."
            }
            speech {
                "All right. Goodbye then."
            }
            shouldEndSession = true
        }
    }

    fun unsupportedIntent() = alexaResponse {
        response {
            simpleCard {
                title = "Unsupported Intent"
                content = "This is unsupported. Please try something else."
            }
            speech {
                "This is unsupported. Please try something else."
            }
            shouldEndSession = true
        }
    }

    @JvmOverloads
    fun retryIntent(attributes: MutableMap<String, Any?>,
                    retryMsg: String = "I'm sorry, I couldn't understand what you have said. Could you say it again?",
                    endMessage: String = "Thank you for using ${getSkillName()}"): AlexaResponse {
        val attempts = attributes.getAttr<Int>("retry")
        if (attempts != null) {
            return alexaResponse {
                response {
                    shouldEndSession = true
                    speech {
                        endMessage
                    }
                }
            }
        }
        attributes["retry"] = 1
        return alexaResponse {
            response {
                speech {
                    retryMsg
                }
                shouldEndSession = false
            }
            sessionAttributes { attributes }
        }
    }

    fun helpIntent() = alexaResponse {
        response {
            simpleCard {
                title = "Help"
                content = "Well, actually I don't know how to help. Sorry."
            }
            speech {
                "Well, actually I don't know how to help. Sorry."
            }
            shouldEndSession = true
        }
    }

    fun hasDisplay(context: Context): Boolean {
        val supportedInterfaces = context.system.device?.supportedInterfaces
        val display = supportedInterfaces?.display
        return display?.templateVersion.equals("1.0") &&
                display?.markupVersion.equals("1.0")
    }

    /**
     * Read a given slot and returns the value only if ER matches successfully
     * @param Slot to be read
     * @return value from the given slot
     */
    fun readSlot(slot: Slot?): String? {
        val resolution = slot?.resolutions?.resolutionsPerAuthority?.first()
        return resolution?.status?.code?.let {
            if (it == StatusCode.ER_SUCCESS_MATCH) resolution.values.first().value.name else null
        }
    }
}