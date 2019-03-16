/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.util

import com.hp.kalexa.model.interfaces.display.Image
import com.hp.kalexa.model.interfaces.display.ImageInstance
import com.hp.kalexa.model.interfaces.display.PlainText
import com.hp.kalexa.model.interfaces.display.RichText
import org.apache.commons.lang3.StringEscapeUtils

object SpeechUtil {

    fun makePlainText(text: String): PlainText {
        val plainText = PlainText()
        plainText.text = text
        return plainText
    }

    fun makeRichText(text: String): RichText {
        val richText = RichText()
        richText.text = text
        return richText
    }

    fun escapeXML(text: String): String {
        return StringEscapeUtils.escapeXml(text)
    }

    fun wrapTextInExtraSmallFont(text: String): String {
        return "<font size=\"2\">$text</font>"
    }

    fun wrapTextInSmallFont(text: String): String {
        return "<font size=\"3\">$text</font>"
    }

    fun wrapTextInMediumFont(text: String): String {
        return "<font size=\"5\">$text</font>"
    }

    fun wrapTextInLargeFont(text: String): String {
        return "<font size=\"7\">$text</font>"
    }

    fun wrapTextInBold(text: String): String {
        return "<b>$text</b>"
    }

    fun wrapTextInItalics(text: String): String {
        return "<i>$text</i>"
    }

    fun wrapTextInUnderline(text: String): String {
        return "<u>$text</u>"
    }

    fun wrapTextInAction(text: String, token: String): String {
        return "<action token=\"$token\">$text</action>"
    }

    fun inlineImageTag(image: Image): String {
        val sources = image.sources
        val imageInstance: ImageInstance
        if (sources.isNotEmpty()) {
            imageInstance = sources[0]
        } else {
            return ""
        }

        return inlineImageTag(imageInstance.url, imageInstance.widthPixels ?: 0, imageInstance.heightPixels ?: 0)
    }

    fun inlineImageTag(url: String, width: Int, height: Int): String {
        return "<img src=\"$url\" width=\"$width\" height=\"$height\"></img>"
    }

    val LINE_BREAK = "<br/>"
}
