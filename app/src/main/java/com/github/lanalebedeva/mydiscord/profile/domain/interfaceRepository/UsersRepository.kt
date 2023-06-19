package com.github.lanalebedeva.mydiscord.profile.domain.interfaceRepository

import com.github.lanalebedeva.mydiscord.api.state.ResultChat
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

interface UsersRepository {
    suspend fun getUsers(): List<ViewTyped>
    suspend fun getUsersFromZulip(): ResultChat<List<ViewTyped>>
    suspend fun getUsersFromBd(): ResultChat<List<ViewTyped>>
}
