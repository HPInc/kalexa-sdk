/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

import com.hp.kalexa.model.entityresolution.Resolutions
import com.hp.kalexa.model.entityresolution.StatusCode

data class Slot(val name: String,
                val value: String? = null,
                val confirmationStatus: SlotConfirmationStatus,
                val resolutions: Resolutions? = null) {

    /**
     * Read a given slot and returns the value only if ER matches successfully
     * @param Slot to be read
     * @return value from the given slot
     */
    fun resolvedValue(): String? {
        val resolution = resolutions?.resolutionsPerAuthority?.firstOrNull()
        return resolution?.status?.code?.let { statusCode ->
            if (statusCode == StatusCode.ER_SUCCESS_MATCH) resolution.values.firstOrNull()?.value?.name else null
        }
    }
}

