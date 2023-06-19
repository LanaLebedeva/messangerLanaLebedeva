package com.github.lanalebedeva.mydiscord.channel.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.lanalebedeva.mydiscord.channel.data.bd.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllTopic(streams: List<TopicEntity>)

    @Query("SELECT * FROM topic_table WHERE topic_id = :streamId")
    fun getTopicsByStreamId(streamId: Int): Flow<List<TopicEntity>>

    @Query("SELECT * FROM `topic_table`")
    fun getAllTopic(): Flow<List<TopicEntity>>

    @Query("DELETE FROM `topic_table`")
    suspend fun deleteAll()
}
