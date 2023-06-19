package com.github.lanalebedeva.mydiscord.messages.data.dto

import com.squareup.moshi.Json

class ReactionsListDto (
    @Json(name = "reactions")
    val reactionsListDto: List<ReactionsDto>
)
