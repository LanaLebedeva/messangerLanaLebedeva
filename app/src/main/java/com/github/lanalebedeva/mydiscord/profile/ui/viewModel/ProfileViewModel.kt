package com.github.lanalebedeva.mydiscord.profile.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lanalebedeva.mydiscord.profile.data.model.User
import com.github.lanalebedeva.mydiscord.profile.domain.useCase.ProfileUserCase
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    profileUserCase: ProfileUserCase
) : ViewModel() {

    private val _viewState = profileUserCase.getFlowOwnUser()
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
    val viewState: StateFlow<ViewStateChat<User>> = _viewState
}
