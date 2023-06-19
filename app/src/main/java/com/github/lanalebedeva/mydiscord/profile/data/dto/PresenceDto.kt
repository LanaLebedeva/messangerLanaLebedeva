package com.github.lanalebedeva.mydiscord.profile.data.dto

import com.squareup.moshi.Json

class PresenceDto(
    @Json(name = "presence")
    val presence: PresenceName,
)

class PresenceName(
    @Json(name = "aggregated")
    val userPresence: PresenceUser
)

class PresenceUser(
    @Json(name = "status")
    val status: String
)
