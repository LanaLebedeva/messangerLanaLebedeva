package com.github.lanalebedeva.mydiscord.di


import com.github.lanalebedeva.mydiscord.App
import com.github.lanalebedeva.mydiscord.MainActivity
import com.github.lanalebedeva.mydiscord.channel.ui.ChannelsFragment
import com.github.lanalebedeva.mydiscord.channel.ui.CommonStreamFragment
import com.github.lanalebedeva.mydiscord.messages.ui.MessageListFragment
import com.github.lanalebedeva.mydiscord.profile.ui.PeopleFragment
import com.github.lanalebedeva.mydiscord.profile.ui.ProfileFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,])
interface ApplicationComponent {

    fun inject(app: App)
    fun inject(activity: MainActivity)
    fun inject(messageListFragment: MessageListFragment)
    fun inject(channelsFragment: ChannelsFragment)
    fun inject(commonStreamFragment: CommonStreamFragment)
    fun inject(peopleFragment: PeopleFragment)
    fun inject(profileFragment: ProfileFragment)
}
