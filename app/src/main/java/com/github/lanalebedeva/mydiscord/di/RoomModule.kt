package com.github.lanalebedeva.mydiscord.di

import android.content.Context
import androidx.room.Room
import com.github.lanalebedeva.mydiscord.ChatRoomDatabase
import com.github.lanalebedeva.mydiscord.profile.data.bd.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideChatRoomDatabase(context : Context) : ChatRoomDatabase =
        Room.databaseBuilder(context, ChatRoomDatabase::class.java, "chat-db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideUserDao(database: ChatRoomDatabase): UserDao = database.userDao()
}
