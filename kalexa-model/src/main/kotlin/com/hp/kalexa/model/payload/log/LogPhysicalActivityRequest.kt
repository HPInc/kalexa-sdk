package com.hp.kalexa.model.payload.log

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hp.kalexa.model.json.LocalDateTimeSerializer
import com.hp.kalexa.model.payload.Payload
import com.hp.kalexa.model.payload.print.Language
import java.time.LocalDateTime

//class PhysicalActivity(var description: String = "",
//                       @JsonSerialize(using = LocalDateTimeSerializer::class)
//                       var startTime: LocalDateTime = LocalDateTime.now(),
//                       var duration: Float = 0.0f,
//                       var distance: Float = 0.0f) : Payload.RequestType {
//    companion object {
//        @JvmStatic
//        fun builder() = PhysicalActivityBuilder()
//    }
//
//    class PhysicalActivityBuilder {
//        private var description: String = ""
//        private var startTime: LocalDateTime = LocalDateTime.now()
//        private var duration: Float = 0.0f
//        private var distance: Float = 0.0f
//
//        fun startTime(startTime: LocalDateTime) = apply { this.startTime = startTime }
//        fun description(description: String) = apply { this.description = description }
//        fun duration(duration: Float) = apply { this.duration = duration }
//        fun distance(distance: Float) = apply { this.distance = distance }
//        fun build() = PhysicalActivity(description, startTime, duration, distance)
//    }
//}

@JsonTypeName("LogPhysicalActivityRequest")
class LogPhysicalActivityRequest(
//        @JsonProperty("PhysicalActitivy")
//        val physicalActivity: PhysicalActivity,
        @JsonProperty("@version")
        version: String = "1.0",
        @JsonProperty("@language")
        language: Language = Language.EN_US) : Payload(version, language)