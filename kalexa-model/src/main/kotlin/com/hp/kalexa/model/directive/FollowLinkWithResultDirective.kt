package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hp.kalexa.model.Directive
import com.hp.kalexa.model.TargetURI
import com.hp.kalexa.model.json.PayloadSerializer
import com.hp.kalexa.model.payload.Payload

@JsonTypeName("Links.FollowLinkWithResult")
class FollowLinkWithResultDirective(
        val targetURI: TargetURI,
        @JsonSerialize(using = PayloadSerializer::class)
        var payload: Payload<*>,
        val token: String = "none") : Directive()