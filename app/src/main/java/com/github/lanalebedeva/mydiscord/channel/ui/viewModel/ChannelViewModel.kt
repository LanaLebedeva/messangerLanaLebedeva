package com.github.lanalebedeva.mydiscord.channel.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lanalebedeva.mydiscord.channel.data.model.StreamLayoutData
import com.github.lanalebedeva.mydiscord.channel.domain.useCase.ChannelUseCase
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


class ChannelViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase
) : ViewModel() {
    private var searchFilter = ""

    private val searchQueryState: MutableSharedFlow<String> = MutableSharedFlow()

    private val _viewStateSubscribe = getFilterFlowSubscribe()
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
    val viewStateSubscribe: StateFlow<ViewStateChat<StreamLayoutData>> = _viewStateSubscribe


    private val _viewState = getFilterFlow()
        .catch {
            ViewStateChat.Error(it)
        }
        .map {
            ViewStateChat.Data(it)
        }

        .stateIn(
            scope = viewModelScope,
            initialValue = ViewStateChat.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
    val viewState: StateFlow<ViewStateChat<StreamLayoutData>> = _viewState


    private fun getFilterFlowSubscribe(): Flow<StreamLayoutData> {
        return channelUseCase.getFlowSubscribeChannels().map { streamLayoutData ->
            val tempStreamLayoutData = StreamLayoutData()
            streamLayoutData.expandableListDetail.forEach { (title, streamData) ->
                if (title.contains(searchFilter, true)) {
                    tempStreamLayoutData.expandableListDetail[title] = streamData
                }
            }
            tempStreamLayoutData
        }
    }

    private fun getFilterFlow(): Flow<StreamLayoutData> {
        return channelUseCase.getFlowChannels().map { streamLayoutData ->
            val tempStreamLayoutData = StreamLayoutData()
            streamLayoutData.expandableListDetail.forEach { (title, streamData) ->
                if (title.contains(searchFilter, true)) {
                    tempStreamLayoutData.expandableListDetail[title] = streamData
                }
            }
            tempStreamLayoutData
        }
    }

    suspend fun onQueryChanged(query: String) {
        searchQueryState.emit(query)
    }


    //https://flowmarbles.com
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun subscribeToSearchQueryChanges() {
        searchQueryState
            .distinctUntilChanged()
            .debounce(500L)
            .mapLatest(::search)
            .launchIn(viewModelScope)
    }

    private fun search(search: String) {
        searchFilter = search
    }
}
