package com.github.lanalebedeva.mydiscord.messages.data.dto

import com.squareup.moshi.Json

class MessagesInSteamDto (
    @Json(name = "messages")
    val messages: List<MessageDto>
)
