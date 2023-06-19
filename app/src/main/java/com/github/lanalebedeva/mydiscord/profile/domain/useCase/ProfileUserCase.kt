package com.github.lanalebedeva.mydiscord.profile.domain.useCase

import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import com.github.lanalebedeva.mydiscord.profile.data.model.User
import com.github.lanalebedeva.mydiscord.profile.domain.interfaceRepository.ProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

private const val REFRESH_INTERVAL_MS = 5000L

class ProfileUserCase @Inject constructor(
    private val repositoryImpl: ProfileRepository
) {
    fun getFlowOwnUser(): Flow<User> {
        return flow {
            when (val result = repositoryImpl.getOwnUserFromBd()) {
                ResultChat.NothingInBd -> { }
                else -> {
                    emit((result as ResultChat.Success).data)
                }
            }
            val user = repositoryImpl.getOwnUser()
            try {
                while (true) {
                    emit(user)
                    delay(REFRESH_INTERVAL_MS)
                }
            } catch (exception: Exception) {
                emit(user)
            }
        }
    }
}
