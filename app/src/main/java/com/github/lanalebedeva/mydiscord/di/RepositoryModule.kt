package com.github.lanalebedeva.mydiscord.di

import com.github.lanalebedeva.mydiscord.channel.data.repository.ChannelRepositoryImpl
import com.github.lanalebedeva.mydiscord.channel.domain.interfaceRepository.ChannelRepository
import com.github.lanalebedeva.mydiscord.messages.data.repository.MessagesRepositoryImpl
import com.github.lanalebedeva.mydiscord.messages.domain.interfaceRepository.MessagesRepository
import com.github.lanalebedeva.mydiscord.profile.data.repository.ProfileRepositoryImpl
import com.github.lanalebedeva.mydiscord.profile.data.repository.UsersRepositoryImpl
import com.github.lanalebedeva.mydiscord.profile.domain.interfaceRepository.ProfileRepository
import com.github.lanalebedeva.mydiscord.profile.domain.interfaceRepository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Reusable
    fun bindChannelRepository(repository: ChannelRepositoryImpl): ChannelRepository

    @Binds
    @Reusable
    fun bindMessageRepository(repository: MessagesRepositoryImpl): MessagesRepository

    @Binds
    @Reusable
    fun bindProfileRepository(repository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @Singleton
    fun bindUserRepository(repository: UsersRepositoryImpl): UsersRepository
}
