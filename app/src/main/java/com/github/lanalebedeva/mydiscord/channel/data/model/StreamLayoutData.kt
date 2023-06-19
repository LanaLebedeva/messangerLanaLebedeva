package com.github.lanalebedeva.mydiscord.channel.data.model

import com.github.lanalebedeva.mydiscord.R
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import java.util.HashMap

class StreamData(
    val id: Int,
    val title: String,
    val topics: List<String>,
    override val viewType: Int = R.layout.user_item,
    override val uid: String = id.toString()
): ViewTyped

class StreamLayoutData(
    val expandableListDetail: HashMap<String, StreamData> = HashMap<String, StreamData>(),
) {
    fun addStreamData(title: String, streamData: StreamData) {
        expandableListDetail[title] = streamData
    }
}


