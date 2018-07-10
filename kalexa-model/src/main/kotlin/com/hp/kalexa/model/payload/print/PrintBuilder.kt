package com.hp.kalexa.model.payload.print

abstract class PrintBuilder<T> {
    protected lateinit var title: String
    protected var description: String? = null
    protected lateinit var url: String
    protected var version: String = "1"
    protected var language: Language = Language.EN_US

    fun title(block: () -> String) = apply { this.title = block() }
    fun description(block: () -> String) = apply { this.description = block() }
    fun url(block: () -> String) = apply { this.url = block() }
    fun version(block: () -> String) = apply { this.version = block() }

    abstract fun build(): T
}