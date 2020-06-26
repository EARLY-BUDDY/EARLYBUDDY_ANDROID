package com.earlyBuddy.earlybuddy_android

import okhttp3.Interceptor
import okhttp3.Response

class CookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().header("Content-Type", "application/json").header("jwt",TransportMap.jwt)
                .build()
        return chain.proceed(request)
    }
}