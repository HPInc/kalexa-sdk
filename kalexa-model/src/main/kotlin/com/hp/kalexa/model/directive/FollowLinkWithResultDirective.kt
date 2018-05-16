package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hp.kalexa.model.TargetURI
import com.hp.kalexa.model.json.PayloadSerializer
import com.hp.kalexa.model.payload.Payload

@Deprecated("It doesn't work anymore, use SendRequestDirective instead")
@JsonTypeName("Links.FollowLinkWithResult")
data class FollowLinkWithResultDirective(
        val targetURI: TargetURI,
        @JsonSerialize(using = PayloadSerializer::class)
        var payload: Payload<*>,
        val token: String = "none") : Directive()