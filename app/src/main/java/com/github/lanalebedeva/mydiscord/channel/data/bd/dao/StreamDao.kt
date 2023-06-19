package com.github.lanalebedeva.mydiscord.channel.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.lanalebedeva.mydiscord.channel.data.bd.entity.StreamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StreamDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllStream(streams: List<StreamEntity>)

    @Query("SELECT * FROM `stream_table`")
    fun getAllStreams(): Flow<List<StreamEntity>>

    @Query("DELETE FROM `stream_table`")
    suspend fun deleteAll()
}
