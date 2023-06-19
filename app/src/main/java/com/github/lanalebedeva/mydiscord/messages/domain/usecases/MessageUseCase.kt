package com.github.lanalebedeva.mydiscord.messages.domain.usecases

import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import com.github.lanalebedeva.mydiscord.messages.domain.interfaceRepository.MessagesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import javax.inject.Inject

private const val REFRESH_INTERVAL_MS = 5000L

interface MessageUseCaseI {
    fun getFlowMessage(
        topicName: String,
        streamName: String,
        numBefore: Int,
        numAfter: Int
    ): Flow<List<ViewTyped>>

    suspend fun sendMessageInStream(
        typeMessage: String,
        stream: String,
        topic: String,
        content: String
    ): ResultChat<Any>

    suspend fun addMessageReaction(
        messageId: String,
        emojiName: String,
    ): ResultChat<Any>


}

class MessageUseCase @Inject constructor(
    private val repositoryImpl: MessagesRepository,
): MessageUseCaseI {

    override fun getFlowMessage(topicName: String, streamName: String, numBefore: Int, numAfter: Int) =
        flow {
            while (true) {
                emit(getMessagesInStream(topicName, streamName, numBefore, numAfter))
                delay(REFRESH_INTERVAL_MS)
            }
        }

    override suspend fun sendMessageInStream(
        typeMessage: String,
        stream: String,
        topic: String,
        content: String
    ) = repositoryImpl.sendMessageInStream(typeMessage, stream, topic, content)

    override suspend fun addMessageReaction(
        messageId: String,
        emojiName: String,
    ) = repositoryImpl.addMessageReaction(messageId, emojiName)

    private suspend fun getMessagesInStream(
        topicName: String,
        streamName: String,
        numBefore: Int,
        numAfter: Int
    ): List<ViewTyped> =
        repositoryImpl.getMessagesInStream(topicName, streamName, numBefore, numAfter)


}
