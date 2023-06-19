package com.github.lanalebedeva.mydiscord.profile.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.lanalebedeva.mydiscord.profile.data.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    val id : Int,
    val name : String,
    val email : String,
    val avatar : String
)

fun UserEntity.toViewTyped(statusOnline: Boolean, statusIdle: Boolean): User {
    return User(
        id = id,
        name = name,
        email = email,
        avatar = avatar,
        statusIdle = statusIdle,
        statusOnline = statusOnline,
    )
}
