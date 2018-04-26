package com.hp.kalexa.model.directive

import com.fasterxml.jackson.annotation.JsonTypeName
import com.hp.kalexa.model.interfaces.display.Template

@JsonTypeName("Display.RenderTemplate")
class RenderTemplateDirective(var template: Template? = null) : Directive()


