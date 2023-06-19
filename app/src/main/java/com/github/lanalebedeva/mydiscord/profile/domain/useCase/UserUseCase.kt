package com.github.lanalebedeva.mydiscord.profile.domain.useCase

import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import com.github.lanalebedeva.mydiscord.profile.domain.interfaceRepository.UsersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import javax.inject.Inject

private const val REFRESH_INTERVAL_MS = 55000L

class UserUseCase @Inject constructor(
    private val repositoryImpl: UsersRepository
) {

    val users: Flow<List<ViewTyped>> by lazy {
        flow {
            when (val result = repositoryImpl.getUsersFromBd()) {
                ResultChat.NothingInBd -> {
                }
                else -> {
                    emit((result as ResultChat.Success).data)
                }
            }
            while (true) {
                when (val result = repositoryImpl.getUsersFromZulip()) {
                    is ResultChat.Error -> { }
                    else -> {
                        emit((result as ResultChat.Success).data)
                    }

                }
                delay(REFRESH_INTERVAL_MS)
            }
        }
    }

}
