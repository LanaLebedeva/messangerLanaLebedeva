package com.github.lanalebedeva.mydiscord.api.services

import com.github.lanalebedeva.mydiscord.messages.data.dto.MessagesInSteamDto
import com.github.lanalebedeva.mydiscord.profile.data.dto.PresenceDto
import com.github.lanalebedeva.mydiscord.channel.data.dto.StreamsAllDto
import com.github.lanalebedeva.mydiscord.channel.data.dto.StreamsSubscriptionsDto
import com.github.lanalebedeva.mydiscord.channel.data.dto.TopicsInStreamDto
import com.github.lanalebedeva.mydiscord.profile.data.dto.UserDto
import com.github.lanalebedeva.mydiscord.profile.data.dto.UsersAllDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Zulip {

    @GET("streams")
    suspend fun getStreamsAll(): StreamsAllDto

    @GET("streams")
    suspend fun getStreamsAllFlow(): Flow<StreamsAllDto>

    @GET("users/me/subscriptions")
    suspend fun getStreamsSubscriptions(): StreamsSubscriptionsDto

    @GET("users/me/{stream_id}/topics")
    suspend fun getTopicsByStreamId(@Path("stream_id") id: String): TopicsInStreamDto

    @GET("users/me/{stream_id}/topics")
    suspend fun getTopicsByStreamIdFlow(@Path("stream_id") id: String): Flow<TopicsInStreamDto>

    @GET("users")
    suspend fun getUsers(): UsersAllDto

    @GET("users/me")
    suspend fun getOwnUser(): UserDto

    @GET("users/{user_id_or_email}/presence")
    suspend fun getUserPresence(@Path("user_id_or_email") id: String): PresenceDto

    @GET("messages")
    suspend fun getMessagesInStream(@QueryMap map: Map<String, String>): MessagesInSteamDto

    @POST("messages/{message_id}/reactions")
    suspend fun addMessageReaction(
        @Path("message_id") messageId: String,
        @Query("emoji_name") emojiName: String
    )

    @POST("messages")
    suspend fun sendMessageInStream(@QueryMap map: Map<String, String>)
}

