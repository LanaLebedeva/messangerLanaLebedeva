package com.github.lanalebedeva.mydiscord.messages.data.repository

import com.github.lanalebedeva.mydiscord.api.services.Zulip
import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import com.github.lanalebedeva.mydiscord.messages.data.dto.NarrowMessage
import com.github.lanalebedeva.mydiscord.messages.data.dto.toViewTyped
import com.github.lanalebedeva.mydiscord.messages.domain.interfaceRepository.MessagesRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import java.lang.Exception
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val zulip: Zulip
) : MessagesRepository {

    override suspend fun getMessagesInStream(
        topicName: String,
        streamName: String,
        numBefore: Int,
        numAfter: Int
    ): List<ViewTyped> {
        val params = creatingRequest(topicName, streamName, numBefore, numAfter)
        return zulip.getMessagesInStream(params).messages.map { messageDto ->
            messageDto.toViewTyped()
        }
    }

    override suspend fun sendMessageInStream(
        typeMessage: String,
        stream: String,
        topic: String,
        content: String
    ): ResultChat<Any> {
        val request = creatingRequestSendMessage(
            typeMessage,
            stream,
            topic,
            content
        )
        return try {
            ResultChat.Success(
                zulip.sendMessageInStream(request)
            )
        } catch (exception: Exception) {
            ResultChat.Error(exception)
        }
    }

    override suspend fun addMessageReaction(messageId: String, emojiName: String): ResultChat<Any> {
        return try {
            ResultChat.Success(
                zulip.addMessageReaction(messageId, emojiName)
            )
        } catch (exception: Exception) {
            ResultChat.Error(exception)
        }
    }

    private fun creatingRequest(
        topicName: String,
        streamName: String,
        numBefore: Int,
        numAfter: Int
    ): Map<String, String> {
        val narrow = listOf(
            NarrowMessage(operator = "topic", operand = streamName),
        )
        val narrowJson = Json.encodeToString(narrow)
        return mapOf(
            "anchor" to "newest",
            "num_before" to numBefore.toString(),
            "num_after" to numAfter.toString(),
            "narrow" to narrowJson,
            "apply_markdown" to "false"
        )
    }

    private fun creatingRequestSendMessage(
        typeMessage: String,
        stream: String,
        topic: String,
        content: String
    ): Map<String, String> = mapOf(
        "type" to typeMessage,
        "to" to topic,
        "topic" to stream,
        "content" to content,
    )
}
