package com.github.lanalebedeva.mydiscord.stub

import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import com.github.lanalebedeva.mydiscord.messages.domain.usecases.MessageUseCaseI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

class MessageUseCaseStub : MessageUseCaseI {

    var resultProvider: () -> List<ViewTyped> = { emptyList() }
    override fun getFlowMessage(
        topicName: String,
        streamName: String,
        numBefore: Int,
        numAfter: Int
    ): Flow<List<ViewTyped>> =
        flow {
            emit(resultProvider())
        }

    override suspend fun sendMessageInStream(
        typeMessage: String,
        stream: String,
        topic: String,
        content: String
    ): ResultChat<Map<String, String>> = ResultChat.Success(mapOf())

    override suspend fun addMessageReaction(messageId: String, emojiName: String): ResultChat<Any> =
        ResultChat.Success(Unit)
}
