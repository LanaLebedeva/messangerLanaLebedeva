package com.github.lanalebedeva.mydiscord.messages.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.lanalebedeva.mydiscord.R
import com.github.lanalebedeva.mydiscord.messages.data.model.MessageData
import com.github.lanalebedeva.mydiscord.messages.data.model.Reactions
import com.github.lanalebedeva.mydiscord.messages.domain.usecases.MessageUseCaseI
import com.github.lanalebedeva.mydiscord.stub.MessageUseCaseStub
import com.github.lanalebedeva.mydiscord.util.MainDispatcherRule
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

@OptIn(ExperimentalCoroutinesApi::class)
class MessageListViewModelTest {
    @get:Rule
    val viewModelRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `loadTasksForProject by default returns task list`() = runTest {
        val tasks = listOf(
            createMessage(id = 1, senderId = 1, senderFullName = "First", content = "Hello"),
            createMessage(id = 2, senderId = 2, senderFullName = "Second", content = "Hello, Fir"),
            createMessage(id = 3, senderId = 3, senderFullName = "Third", content = "Hello, every"),
        )
        val useCase: MessageUseCaseStub = MessageUseCaseStub().apply {
            resultProvider = { tasks }
        }
        val viewModel = createViewModel(useCase)


        val state = (viewModel.viewState.value)
        assertEquals(state, ViewStateChat.Loading)
//        assertEquals(tasks, (state as ViewStateChat.Data<List<ViewTyped>>).data)
//        assertEquals(tasks, (viewModel.viewState.value as ViewStateChat.Data<List<ViewTyped>>).data)
    }

    private fun createViewModel(useCase: MessageUseCaseI): MessageListViewModel =
        MessageListViewModel(topic = "topic", stream = "stram", messagesUseCase = useCase)

    private fun createMessage(
        id: Int,
        senderId: Int,
        senderFullName: String,
        content: String,
        isMeMessage: Boolean = false,
        reactions: List<Reactions> = emptyList<Reactions>(),
        time: String = "1 фев",
        avatarUrl: String? = null,
    ): ViewTyped = MessageData(
        id = id,
        senderId = senderId,
        senderFullName = senderFullName,
        content = content,
        isMeMessage = isMeMessage,
        reactions = reactions,
        time = time,
        avatarUrl = avatarUrl,
        viewType = R.layout.message_with_reaction_item,
    )
}