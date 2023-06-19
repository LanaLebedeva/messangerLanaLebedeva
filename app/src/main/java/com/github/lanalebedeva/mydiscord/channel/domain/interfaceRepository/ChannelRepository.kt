package com.github.lanalebedeva.mydiscord.channel.domain.interfaceRepository

import com.github.lanalebedeva.mydiscord.channel.data.model.StreamLayoutData

interface ChannelRepository {
    suspend fun getStreams(): StreamLayoutData
    suspend fun getStreamsSubscription(): StreamLayoutData
    suspend fun refreshStream()
}
