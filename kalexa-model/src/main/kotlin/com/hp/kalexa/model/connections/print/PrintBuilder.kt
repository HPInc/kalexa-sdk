/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model.payload.print

import com.hp.kalexa.model.payload.Context

abstract class PrintBuilder<T> {
    protected lateinit var title: String
    protected var description: String? = null
    protected lateinit var url: String
    protected var version: String = "1"
    protected var context: Context? = null

    fun title(block: () -> String) = apply { this.title = block() }
    fun description(block: () -> String) = apply { this.description = block() }
    fun url(block: () -> String) = apply { this.url = block() }
    fun version(block: () -> String) = apply { this.version = block() }
    fun context(block: Context.() -> Unit) = apply { this.context = Context().apply { block() } }

    abstract fun build(): T
}
