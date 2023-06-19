package com.github.lanalebedeva.mydiscord.messages.data.model

import com.github.lanalebedeva.mydiscord.R
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

data class MessageData(
    val id: Int,
    val senderId: Int,
    val senderFullName: String,
    val content: String,
    val isMeMessage: Boolean,
    val reactions: List<Reactions> = listOf(),
    val time: String = "1 фев",
    val avatarUrl: String? = null,
    override val viewType: Int = R.layout.message_with_reaction_item,
) : ViewTyped

