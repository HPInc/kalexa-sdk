/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.services.proactiveevents.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class OrderStatus {
    PREORDER_RECEIVED, ORDER_RECEIVED, ORDER_PREPARING, ORDER_SHIPPED, ORDER_OUT_FOR_DELIVERY, ORDER_DELIVERED;

    companion object {

        @JsonCreator
        fun fromValue(text: String): OrderStatus? {
            for (value in values()) {
                if (value.name == text) {
                    return value
                }
            }
            return null
        }
    }
}
