package com.github.lanalebedeva.mydiscord.profile.data.repository

import com.github.lanalebedeva.mydiscord.api.services.Zulip

import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import com.github.lanalebedeva.mydiscord.profile.data.bd.dao.UserDao
import com.github.lanalebedeva.mydiscord.profile.data.bd.entity.toViewTyped
import com.github.lanalebedeva.mydiscord.profile.data.dto.PresenceDto
import com.github.lanalebedeva.mydiscord.profile.data.dto.toUser
import com.github.lanalebedeva.mydiscord.profile.data.model.User
import com.github.lanalebedeva.mydiscord.profile.domain.interfaceRepository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl
@Inject constructor(
    private val zulip: Zulip,
    private val userDao: UserDao,
) : ProfileRepository {

    override suspend fun getOwnUserFromBd(): ResultChat<User> =
        try {
            ResultChat.Success(
                userDao.getAllUsers()[0].toViewTyped(
                    statusOnline = false,
                    statusIdle = false
                )
            )
        } catch (e: Exception) {
            ResultChat.NothingInBd
        }

    override suspend fun getOwnUser(): User {
        val userDto = zulip.getOwnUser()
        return userDto.toUser(statusIdle = (!userDto.isActive && getUserPresence(userId = userDto.userId).presence.userPresence.status == "idle"))
    }

    private suspend fun getUserPresence(userId: Int): PresenceDto =
        zulip.getUserPresence(userId.toString())
}
