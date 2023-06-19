package com.github.lanalebedeva.mydiscord.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.lanalebedeva.mydiscord.messages.domain.usecases.MessageUseCase
import com.github.lanalebedeva.mydiscord.messages.ui.viewModel.MessageListViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MessageViewModelFactory @AssistedInject constructor(
    @Assisted ("topic") private val topic: String,
    @Assisted ("stream") private val stream: String,
    private val messagesUseCase: MessageUseCase,
): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MessageListViewModel(topic, stream, messagesUseCase) as T
    }
    @AssistedFactory
    interface Factory {
        fun create(@Assisted("topic") topic: String,
                   @Assisted("stream") stream: String): MessageViewModelFactory
    }


}
