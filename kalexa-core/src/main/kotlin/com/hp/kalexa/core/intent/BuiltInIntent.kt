/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.core.intent

enum class BuiltInIntent(val rawValue: String) {
    CANCEL_INTENT("AMAZON.CancelIntent"),
    HELP_INTENT("AMAZON.HelpIntent"),
    MORE_INTENT("AMAZON.MoreIntent"),
    NAVIGATE_SETTINGS_INTENT("AMAZON.NavigateSettingsIntent"),
    NEXT_INTENT("AMAZON.NextIntent"),
    PAGE_DOWN_INTENT("AMAZON.PageDownIntent"),
    PAGE_UP_INTENT("AMAZON.PageUpIntent"),
    PREVIOUS_INTENT("AMAZON.PreviousIntent"),
    SCROLL_DOWN_INTENT("AMAZON.ScrollDownIntent"),
    SCROLL_LEFT_INTENT("AMAZON.ScrollLeftIntent"),
    SCROLL_RIGHT_INTENT("AMAZON.ScrollRightIntent"),
    SCROLL_UP_INTENT("AMAZON.ScrollUpIntent"),
    STOP_INTENT("AMAZON.StopIntent"),
    YES_INTENT("AMAZON.YesIntent"),
    NO_INTENT("AMAZON.NoIntent"),
    FALLBACK_INTENT("AMAZON.FallbackIntent"),
    NAVIGATE_HOME_INTENT("AMAZON.NavigateHomeIntent");

    override fun toString(): String {
        return rawValue
    }

    companion object {
        fun getBuiltInIntent(intent: String): BuiltInIntent? {
            return BuiltInIntent.values().find { it.toString() == intent }
        }
    }
}