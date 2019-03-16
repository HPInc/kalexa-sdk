/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.ui

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
        JsonSubTypes.Type(value = LinkAccountCard::class, name = "LinkAccount"),
        JsonSubTypes.Type(value = StandardCard::class, name = "Standard"),
        JsonSubTypes.Type(value = AskForPermissionsConsentCard::class, name = "AskForPermissionsConsent"),
        JsonSubTypes.Type(value = SimpleCard::class, name = "Simple"))
abstract class Card

@JsonTypeName("LinkAccount")
class LinkAccountCard : Card()

@JsonTypeName("AskForPermissionsConsent")
data class AskForPermissionsConsentCard(val permissions: List<String>? = emptyList()) : Card()

@JsonTypeName("Simple")
data class SimpleCard(
    var title: String = "",
    var content: String = ""
) : Card()

@JsonTypeName("Standard")
data class StandardCard(
    var title: String = "",
    var text: String = "",
    var image: Image? = null
) : Card()
