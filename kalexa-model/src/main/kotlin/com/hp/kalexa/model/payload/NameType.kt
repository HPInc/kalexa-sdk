package com.hp.kalexa.model.payload

import com.fasterxml.jackson.annotation.JsonProperty

enum class NameType {
    @JsonProperty("Print")
    PRINT,
    @JsonProperty("Log")
    LOG,
    @JsonProperty("Schedule")
    SCHEDULE;
}