package com.hp.kalexa.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.hp.kalexa.model.directive.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
        JsonSubTypes.Type(value = LaunchDirective::class, name = "VideoApp.Launch"),
        JsonSubTypes.Type(value = StopDirective::class, name = "AudioPlayer.Stop"),
        JsonSubTypes.Type(value = ConfirmSlotDirective::class, name = "Dialog.ConfirmSlot"),
        JsonSubTypes.Type(value = PlayDirective::class, name = "AudioPlayer.Play"),
        JsonSubTypes.Type(value = RenderTemplateDirective::class, name = "Display.RenderTemplate"),
        JsonSubTypes.Type(value = ElicitSlotDirective::class, name = "Dialog.ElicitSlot"),
        JsonSubTypes.Type(value = ClearQueueDirective::class, name = "AudioPlayer.ClearQueue"),
        JsonSubTypes.Type(value = DelegateDirective::class, name = "Dialog.Delegate"),
        JsonSubTypes.Type(value = HintDirective::class, name = "Hint"),
        JsonSubTypes.Type(value = ConfirmIntentDirective::class, name = "Dialog.ConfirmIntent"))
abstract class Directive

