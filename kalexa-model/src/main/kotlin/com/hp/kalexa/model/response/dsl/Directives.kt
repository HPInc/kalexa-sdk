/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.response.dsl

import com.hp.kalexa.model.directive.ConfirmIntentDirective
import com.hp.kalexa.model.directive.ConfirmSlotDirective
import com.hp.kalexa.model.directive.DelegateDirective
import com.hp.kalexa.model.directive.Directive
import com.hp.kalexa.model.directive.HintDirective
import com.hp.kalexa.model.directive.LaunchDirective
import com.hp.kalexa.model.directive.RenderTemplateDirective
import com.hp.kalexa.model.interfaces.display.BodyTemplate1
import com.hp.kalexa.model.interfaces.display.BodyTemplate2
import com.hp.kalexa.model.interfaces.display.BodyTemplate3
import com.hp.kalexa.model.interfaces.display.BodyTemplate6
import com.hp.kalexa.model.interfaces.display.BodyTemplate7
import com.hp.kalexa.model.interfaces.display.ListTemplate1
import com.hp.kalexa.model.interfaces.display.ListTemplate2
import com.hp.kalexa.model.interfaces.display.PlainTextHint
import com.hp.kalexa.model.interfaces.display.Template
import com.hp.kalexa.model.interfaces.video.VideoItem
import com.hp.kalexa.model.response.annotation.AlexaResponseDsl

@AlexaResponseDsl
class Directives : ArrayList<Directive>() {
    private var metadata: com.hp.kalexa.model.interfaces.video.Metadata? = null

    fun templateDirective(block: RenderTemplateDirective.() -> Unit) {
        add(RenderTemplateDirective().apply { block() })
    }

    fun metadata(block: com.hp.kalexa.model.interfaces.video.Metadata.() -> Unit) {
        metadata = com.hp.kalexa.model.interfaces.video.Metadata().apply { block() }
    }

    fun playDirective(block: PlayDirectiveBuilder.() -> Unit) {
        add(PlayDirectiveBuilder().apply { apply(block) }.build())
    }

    fun videoDirective(block: VideoItem.() -> Unit) {
        val videoItem = VideoItem().apply { block() }
        videoItem.metadata = videoItem.metadata ?: metadata
        val videoDirective = LaunchDirective()
        videoDirective.videoItem = videoItem
        add(videoDirective)
    }

    fun hintDirective(block: PlainTextHint.() -> Unit) {
        val hint = PlainTextHint().apply { block() }
        val hintDirective = HintDirective()
        hintDirective.hint = hint
        add(hintDirective)
    }

    fun sendRequestDirective(block: SendRequestDirectiveBuilder.() -> Unit) {
        add(SendRequestDirectiveBuilder().apply { block() }.build())
    }

    fun sendResponseDirective(block: SendResponseDirectiveBuilder.() -> Unit) {
        add(SendResponseDirectiveBuilder().apply { block() }.build())
    }

    fun delegateDirective(block: (DelegateDirective.() -> Unit)) {
        add(DelegateDirective().apply { block() })
    }

    fun confirmIntentDirective(block: (ConfirmIntentDirective.() -> Unit)) {
        add(ConfirmIntentDirective().apply { block() })
    }

    fun confirmSlotDirective(block: (ConfirmSlotDirective.() -> Unit)) {
        add(ConfirmSlotDirective().apply { block() })
    }

    fun renderTemplateDirective(block: (RenderTemplateDirective.() -> Template)) {
        add(RenderTemplateDirective().apply { template = block() })
    }

    fun template(block: () -> Template): Template {
        return block()
    }

    fun listTemplate2(block: (ListTemplateBuilder.() -> Unit)): ListTemplate2 {
        return ListTemplateBuilder().apply { block() }.buildListTemplate2()
    }

    fun listTemplate1(block: (ListTemplateBuilder.() -> Unit)): ListTemplate1 {
        return ListTemplateBuilder().apply { block() }.buildListTemplate1()
    }

    fun bodyTemplate7(block: (BodyTemplate7.() -> Unit)): BodyTemplate7 {
        return BodyTemplate7().apply { block() }
    }

    fun bodyTemplate6(block: (BodyTemplate6.() -> Unit)): BodyTemplate6 {
        return BodyTemplate6().apply { block() }
    }

    fun bodyTemplate3(block: (BodyTemplate3.() -> Unit)): BodyTemplate3 {
        return BodyTemplate3().apply { block() }
    }

    fun bodyTemplate2(block: (BodyTemplate2.() -> Unit)): BodyTemplate2 {
        return BodyTemplate2().apply { block() }
    }

    fun bodyTemplate1(block: (BodyTemplate1.() -> Unit)): BodyTemplate1 {
        return BodyTemplate1().apply { block() }
    }

    fun directive(block: () -> Directive) {
        add(block())
    }
}
