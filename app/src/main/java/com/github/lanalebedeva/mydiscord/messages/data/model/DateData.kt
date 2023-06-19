package com.github.lanalebedeva.mydiscord.messages.data.model

import com.github.lanalebedeva.mydiscord.R
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

data class DateData(
    val id: Int,
    val date: String,
    override val viewType: Int = R.layout.data_item,
    override val uid: String = id.toString()
) : ViewTyped
