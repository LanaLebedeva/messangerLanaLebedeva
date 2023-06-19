package com.github.lanalebedeva.mydiscord

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.lanalebedeva.mydiscord.channel.data.bd.entity.StreamEntity
import com.github.lanalebedeva.mydiscord.channel.data.bd.entity.TopicEntity
import com.github.lanalebedeva.mydiscord.profile.data.bd.dao.UserDao
import com.github.lanalebedeva.mydiscord.profile.data.bd.entity.UserEntity

@Database(
    entities = [
        StreamEntity::class,
        TopicEntity::class,
        UserEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class ChatRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
