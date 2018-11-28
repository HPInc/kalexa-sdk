package com.hp.kalexa.model.request

import com.hp.kalexa.model.Context
import com.hp.kalexa.model.Session


class AlexaRequestEnvelope<out T : Request>(
        val version: String,
        val session: Session?,
        val context: Context,
        val request: T)