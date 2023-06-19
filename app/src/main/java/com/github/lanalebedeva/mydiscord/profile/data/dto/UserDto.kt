package com.github.lanalebedeva.mydiscord.profile.data.dto

import com.github.lanalebedeva.mydiscord.profile.data.bd.entity.UserEntity
import com.github.lanalebedeva.mydiscord.profile.data.model.User
import com.squareup.moshi.Json
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

class UserDto(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "email")
    val email: String,
    @Json(name = "full_name")
    val nameUser: String,
    @Json(name = "is_active")
    val isActive: Boolean,
    @Json(name = "avatar_url")
    val avatar: String?,
)

fun UserDto.toViewTyped(): ViewTyped {
    return User(
        id = userId,
        name = nameUser,
        email = email,
        avatar = avatar,
        statusOnline = isActive,
    )
}

fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        id = userId,
        name = nameUser,
        email = email,
        avatar = avatar.toString(),
    )
}

//fun User.toViewTyped(): ViewTyped {
//    return User(
//        id = id,
//        name = name,
//        email = email,
//        avatar = avatar,
//        statusIdle =
//        statusOnline = statusOnline,
//    )
//}

fun UserDto.toUser(statusIdle: Boolean): User {
    return User(
        id = userId,
        name = nameUser,
        email = email,
        avatar = avatar,
        statusIdle = statusIdle,
        statusOnline = isActive,
    )
}


