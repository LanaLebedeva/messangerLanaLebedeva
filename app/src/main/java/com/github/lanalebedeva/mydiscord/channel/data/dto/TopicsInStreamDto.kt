package com.github.lanalebedeva.mydiscord.channel.data.dto

import com.github.lanalebedeva.mydiscord.channel.data.model.StreamData
import com.squareup.moshi.Json

class TopicsInStreamDto(
    @Json(name = "topics")
    val topics: List<TopicDto>
)

fun TopicsInStreamDto.toStreamData(titleDto: String): StreamData {
    return StreamData(
        id = 0,
        title = titleDto,
        topics = topics.map { topicDto ->
            topicDto.nameTopic
        },
    )
}
