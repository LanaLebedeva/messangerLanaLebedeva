package com.github.lanalebedeva.mydiscord.channel.data.dto

import com.squareup.moshi.Json

class StreamsAllDto(
    @Json(name = "streams")
    val streams: List<StreamDto>
)
