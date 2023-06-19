package com.github.lanalebedeva.mydiscord.channel.data.repository

import com.github.lanalebedeva.mydiscord.api.services.Zulip
import com.github.lanalebedeva.mydiscord.channel.data.dto.StreamDto
import com.github.lanalebedeva.mydiscord.channel.data.dto.StreamsAllDto
import com.github.lanalebedeva.mydiscord.channel.data.dto.StreamsSubscriptionsDto
import com.github.lanalebedeva.mydiscord.channel.data.dto.TopicsInStreamDto
import com.github.lanalebedeva.mydiscord.channel.data.dto.toStreamData
import com.github.lanalebedeva.mydiscord.channel.data.model.StreamLayoutData
import com.github.lanalebedeva.mydiscord.channel.domain.interfaceRepository.ChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ChannelRepositoryImpl @Inject constructor(
    private val zulip: Zulip
) : ChannelRepository {
    override suspend fun getStreams(): StreamLayoutData {
        val streamLayoutData = StreamLayoutData()
        val latestChannels: List<StreamDto> =
            getStreamAll().streams
        latestChannels.map { streamDto ->
            val topicsInStreamDto =
                getTopic(streamDto.streamId)
            streamLayoutData.addStreamData(
                streamDto.nameStream,
                topicsInStreamDto.toStreamData(streamDto.nameStream)
            )
        }
        return streamLayoutData
    }

    override suspend fun getStreamsSubscription(): StreamLayoutData {
        val streamLayoutData = StreamLayoutData()
        val latestChannels: List<StreamDto> =
            getStreamsSubscriptions().streamsSubscriptions
        latestChannels.map { streamDto ->
            val topicsInStreamDto =
                getTopic(streamDto.streamId)
            streamLayoutData.addStreamData(
                streamDto.nameStream,
                topicsInStreamDto.toStreamData(streamDto.nameStream)
            )
        }
        return streamLayoutData
    }

    override suspend fun refreshStream() {
        withContext(Dispatchers.IO) {
            // todo извлечь данные из сети и сохранить в БД
        }
    }

    private suspend fun getStreamAll(): StreamsAllDto = zulip.getStreamsAll()

    private suspend fun getStreamsSubscriptions(): StreamsSubscriptionsDto =
        zulip.getStreamsSubscriptions()

    private suspend fun getTopic(streamId: Int): TopicsInStreamDto =
        zulip.getTopicsByStreamId(streamId.toString())
}
