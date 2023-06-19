package com.github.lanalebedeva.mydiscord.api.interceptor

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "нужно получить свой ключ"
private const val AUTH_EMAIL = "testzulip@mail.ru"

class HeaderInterceptor: Interceptor {
    private val basicAccessAuthentication = Credentials.basic(
        AUTH_EMAIL,
        API_KEY)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", basicAccessAuthentication)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}
