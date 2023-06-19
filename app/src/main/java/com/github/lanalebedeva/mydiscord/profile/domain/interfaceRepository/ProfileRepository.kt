package com.github.lanalebedeva.mydiscord.profile.domain.interfaceRepository

import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import com.github.lanalebedeva.mydiscord.profile.data.model.User

interface ProfileRepository {
    suspend fun getOwnUser(): User

    suspend fun getOwnUserFromBd(): ResultChat<User>
}
