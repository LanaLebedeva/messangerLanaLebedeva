package com.github.lanalebedeva.mydiscord.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.lanalebedeva.mydiscord.channel.ui.viewModel.ChannelViewModel
import com.github.lanalebedeva.mydiscord.profile.ui.viewModel.PeopleViewModel
import com.github.lanalebedeva.mydiscord.profile.ui.viewModel.ProfileViewModel
import com.github.lanalebedeva.mydiscord.viewModel.ChatViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PeopleViewModel::class)
    fun bindPeopleViewModel(peopleViewModel: PeopleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChannelViewModel::class)
    fun bindChannelViewModel(channelViewModel: ChannelViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ChatViewModelFactory): ViewModelProvider.Factory
}
