package com.github.lanalebedeva.mydiscord.profile.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.lanalebedeva.mydiscord.profile.data.bd.entity.UserEntity


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(item: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(items: List<UserEntity>)

    @Query("SELECT * FROM `user_table`")
    suspend fun getAllUsers(): List<UserEntity>
}
