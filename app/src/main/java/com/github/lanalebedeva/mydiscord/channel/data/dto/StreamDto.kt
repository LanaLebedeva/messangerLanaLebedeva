package com.github.lanalebedeva.mydiscord.channel.data.dto

import com.squareup.moshi.Json

class StreamDto(
    @Json(name = "stream_id")
    val streamId: Int,
    @Json(name = "name")
    val nameStream: String,
)
