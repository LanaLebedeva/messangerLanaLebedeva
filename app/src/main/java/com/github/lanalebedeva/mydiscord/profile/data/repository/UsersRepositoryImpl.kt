package com.github.lanalebedeva.mydiscord.profile.data.repository

import android.util.Log
import com.github.lanalebedeva.mydiscord.api.services.Zulip
import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import com.github.lanalebedeva.mydiscord.profile.data.bd.dao.UserDao
import com.github.lanalebedeva.mydiscord.profile.data.bd.entity.toViewTyped
import com.github.lanalebedeva.mydiscord.profile.data.dto.toUserEntity
import com.github.lanalebedeva.mydiscord.profile.data.dto.toViewTyped
import com.github.lanalebedeva.mydiscord.profile.domain.interfaceRepository.UsersRepository
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import java.lang.Exception
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val zulip: Zulip,
    private val userDao: UserDao,
) : UsersRepository {

    override suspend fun getUsers(): List<ViewTyped> {
        return zulip.getUsers().members.map { userEntity ->
            userEntity.toViewTyped()
        }
    }

    override suspend fun getUsersFromZulip(): ResultChat<List<ViewTyped>> {
        return try {
            val result = zulip.getUsers().members
            try {
                userDao.insertAllUsers(
                    result.map { userDto ->
                        userDto.toUserEntity()
                    }
                )
            } catch (e:Exception) {
                Log.d("UsersRepositoryImpl", e.toString())
            }
            ResultChat.Success(result.map { userDto ->
                userDto.toViewTyped()
            }
            )
        } catch (e:Exception) {
            ResultChat.Error(e)
        }
    }

   override suspend fun getUsersFromBd(): ResultChat<List<ViewTyped>> {
        val userBd = userDao.getAllUsers()
        return if (userBd.isEmpty()) {
            ResultChat.NothingInBd
        } else {
            ResultChat.Success(userBd.map {userEntity ->
                userEntity.toViewTyped(statusOnline = false, statusIdle = false)
            })
        }
    }
}
