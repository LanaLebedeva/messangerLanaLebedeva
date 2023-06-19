package com.github.lanalebedeva.mydiscord.profile.data.dto

import com.squareup.moshi.Json

class UsersAllDto(
    @Json(name = "members")
    val members: List<UserDto>
)
