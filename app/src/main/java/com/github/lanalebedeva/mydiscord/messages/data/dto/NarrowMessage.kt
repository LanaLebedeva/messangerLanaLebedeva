package com.github.lanalebedeva.mydiscord.messages.data.dto

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class NarrowMessage(
    @Json(name = "operator")
    val operator: String,
    @Json(name = "operand")
    val operand: String,
)
