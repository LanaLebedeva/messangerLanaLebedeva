package com.github.lanalebedeva.mydiscord.messages.data.dto

import com.github.lanalebedeva.mydiscord.messages.data.model.MessageData
import com.github.lanalebedeva.mydiscord.messages.data.model.Reactions
import com.squareup.moshi.Json
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

class ReactionsDto(
    @Json(name = "emoji_name")
    val emojiNameDto: String,
    @Json(name = "emoji_code")
    val emojiCodeDto: String,
    @Json(name = "reaction_type")
    val reactionTypeDto: String,
    @Json(name = "user_id")
    val userIdDto: Int,
)

class MessageDto(
    @Json(name = "avatar_url")
    val avatarUrlDto: String,
    @Json(name = "content")
    val contentMessageDto: String,
    @Json(name = "id")
    val idDto: Int,
    @Json(name = "sender_full_name")
    val senderFullNameDto: String,
    @Json(name = "sender_id")
    val senderIdDto: Int,
    @Json(name = "is_me_message")
    val isMeMessageDto: Boolean,
    @Json(name = "reactions")
    val reactionsDto: List<ReactionsDto>,
    @Json(name = "timestamp")
    val timestampDto: Long,
)

fun MessageDto.toViewTyped(): ViewTyped {

    val reactionMap: MutableMap<String, Reactions> = mutableMapOf()
    reactionsDto.forEach { reactionsDto ->
        if (reactionMap[reactionsDto.emojiCodeDto] == null) {
            reactionMap[reactionsDto.emojiCodeDto] = Reactions(
                id = 0,
                name = reactionsDto.emojiNameDto,
                count = 1,
                userId = reactionsDto.userIdDto,
                emoji = convertToUnicode(reactionsDto.emojiCodeDto, reactionsDto.reactionTypeDto),
                emojiType = reactionsDto.reactionTypeDto,
                emojiCode = reactionsDto.emojiCodeDto
            )
        } else {
            reactionMap[reactionsDto.emojiCodeDto]?.count =
                reactionMap[reactionsDto.emojiCodeDto]?.count?.plus(
                    1
                )!!
        }
    }
    return MessageData(
        id = idDto,
        senderId = senderIdDto,
        senderFullName = senderFullNameDto,
        content = contentMessageDto,
        isMeMessage = isMeMessageDto,
        reactions = reactionMap.map {
            it.value
        },
        time = timestampDto.toString(),//"1 фев", TODO
        avatarUrl = avatarUrlDto,
    )
}

fun convertToUnicode(emojiCodeDto: String, type: String): String =
    when (type) {
        "unicode_emoji" -> String(Character.toChars(Integer.parseInt(emojiCodeDto, 16)))
        else -> "□"
    }

