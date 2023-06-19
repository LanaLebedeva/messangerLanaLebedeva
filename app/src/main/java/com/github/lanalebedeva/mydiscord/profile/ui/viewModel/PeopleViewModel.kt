package com.github.lanalebedeva.mydiscord.profile.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lanalebedeva.mydiscord.profile.domain.useCase.UserUseCase
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import javax.inject.Inject

class PeopleViewModel @Inject constructor(
    peopleUseCase: UserUseCase,
) : ViewModel() {

    private val _viewState = peopleUseCase.users  //flowUsers
        .catch {
            ViewStateChat.Error(it)
        }
        .map {
            Log.d("PeopleViewModel", "_view_state")
            ViewStateChat.Data(it)
        }.stateIn(
            scope = viewModelScope,
            initialValue = ViewStateChat.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    val viewState: StateFlow<ViewStateChat<List<ViewTyped>>> = _viewState
}
