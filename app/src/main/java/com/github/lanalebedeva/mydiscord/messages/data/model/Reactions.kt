package com.github.lanalebedeva.mydiscord.messages.data.model

class Reactions(
    val id: Int,
    val name: String,
    var count: Int = 0,
    val userId: Int = 0,
    val emoji: String = "\uD83D\uDC79",
    val emojiType: String = "",
    val emojiCode: String = "",
)
