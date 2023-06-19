package com.github.lanalebedeva.mydiscord.messages.domain.interfaceRepository

import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

interface MessagesRepository {

    suspend fun getMessagesInStream(
        topicName: String,
        streamName: String,
        numBefore: Int,
        numAfter: Int
    ): List<ViewTyped>

    suspend fun sendMessageInStream(
        typeMessage: String,
        stream: String,
        topic: String,
        content: String,
    ): ResultChat<Any>

    suspend fun addMessageReaction(
        messageId: String,
        emojiName: String,
    ): ResultChat<Any>
}
