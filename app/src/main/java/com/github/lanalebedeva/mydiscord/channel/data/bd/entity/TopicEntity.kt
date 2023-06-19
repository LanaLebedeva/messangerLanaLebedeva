package com.github.lanalebedeva.mydiscord.channel.data.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "topic_table",
//    foreignKeys = [ForeignKey(
//        StreamEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("id"),
//        onDelete = ForeignKey.CASCADE
//    )]
)
data class TopicEntity (
@PrimaryKey
@ColumnInfo(name = "topic_id")
val id: Int,
@ColumnInfo(name = "name_topic")
val topicName: String
)
