package com.github.lanalebedeva.mydiscord.channel.data.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stream_table")
class StreamEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stream_id") val id: Int,
    @ColumnInfo(name = "name_stream") val title: String
    )
