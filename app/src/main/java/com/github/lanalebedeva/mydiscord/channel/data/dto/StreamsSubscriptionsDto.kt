package com.github.lanalebedeva.mydiscord.channel.data.dto

import com.squareup.moshi.Json

class StreamsSubscriptionsDto(
    @Json(name = "subscriptions")
    val streamsSubscriptions: List<StreamDto>
)
