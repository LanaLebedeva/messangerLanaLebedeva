package com.github.lanalebedeva.mydiscord.messages.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lanalebedeva.mydiscord.messages.domain.usecases.MessageUseCaseI
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import javax.inject.Inject

const val NUM_BEFORE = 50
const val NUM_AFTER = 0

internal class MessageListViewModel @Inject constructor(
    private val topic: String,
    private val stream: String,
    private val messagesUseCase: MessageUseCaseI
) : ViewModel() {

    private val _viewState = messagesUseCase.getFlowMessage(topic, stream, NUM_BEFORE, NUM_AFTER)
        .catch {
            ViewStateChat.Error(it)
        }
        .map {
            ViewStateChat.Data(it)
        }.stateIn(
            scope = viewModelScope,
            initialValue = ViewStateChat.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
    val viewState: StateFlow<ViewStateChat<List<ViewTyped>>> = _viewState

    fun addMessage(message: String) {
        if (message.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                messagesUseCase.sendMessageInStream(
                    "stream",
                    stream,
                    topic,
                    message
                )
            }
        }
    }

    fun changeReaction(messageId: String, emojiName: String, change: Int) {
        if (change > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                messagesUseCase.addMessageReaction(messageId, emojiName)
            }
        } else {
            Log.d("MessageListViewModel", "убрать emoji ${emojiName}")
        }
    }
}
