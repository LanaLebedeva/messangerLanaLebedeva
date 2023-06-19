package com.github.lanalebedeva.mydiscord.channel.domain.useCase

import com.github.lanalebedeva.mydiscord.channel.data.model.StreamLayoutData
import com.github.lanalebedeva.mydiscord.channel.domain.interfaceRepository.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChannelUseCase @Inject constructor(
    private val repositoryImpl: ChannelRepository
) {

    fun getFlowChannels(): Flow<StreamLayoutData> {
        return flow {
            emit(repositoryImpl.getStreams())
        }
    }

    fun getFlowSubscribeChannels(): Flow<StreamLayoutData> {
        return flow {
            emit(repositoryImpl.getStreamsSubscription())
        }
    }
//    val flowChannels: Flow<StreamLayoutData> by lazy {
//        flow {
//            emit(repositoryImpl.getStreams())
//        }
//    }


//    val flowSubscribeChannels: Flow<StreamLayoutData> by lazy {
//        flow {
//            emit(repositoryImpl.getStreamsSubscription())
//        }
//    }
}

