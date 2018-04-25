package com.hp.kalexa.model.payload.log

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hp.kalexa.model.json.LocalDateTimeSerializer
import java.time.LocalDateTime

class PhysicalActivity(var description: String = "",
                       @JsonSerialize(using = LocalDateTimeSerializer::class)
                       var startTime: LocalDateTime = LocalDateTime.now(),
                       var duration: Float = 0.0f,
                       var distance: Float = 0.0f) : Log.LogType