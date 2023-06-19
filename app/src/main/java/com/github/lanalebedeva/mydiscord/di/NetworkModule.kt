package com.github.lanalebedeva.mydiscord.di

import com.github.lanalebedeva.mydiscord.api.interceptor.HeaderInterceptor
import com.github.lanalebedeva.mydiscord.api.services.Zulip
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Reusable
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient {
        val logger: Interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(logger)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
    @Reusable
    @Provides
    fun provideZulip(okHttpClient: OkHttpClient): Zulip {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(Zulip::class.java)
    }


    companion object {
        private const val BASE_URL = "https://tinkoff-android-spring-2023.zulipchat.com/api/v1/"
        private const val TIMEOUT = 60L
    }
}
