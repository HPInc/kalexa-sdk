package com.hp.kalexa.model

import com.hp.kalexa.model.entitiyresolution.Resolutions

data class Slot(val name: String,
                val value: String? = null,
                val confirmationStatus: SlotConfirmationStatus,
                val resolutions: Resolutions? = null)

