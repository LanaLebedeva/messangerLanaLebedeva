package com.github.lanalebedeva.mydiscord.di

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class, NetworkModule::class, ViewModelModule::class, RoomModule::class])
class ApplicationModule(@param:NonNull private val context: Context) {
    @Singleton
    @Provides
    @NonNull
    fun provideContext(): Context {
        return context
    }
}
