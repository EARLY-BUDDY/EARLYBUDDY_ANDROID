package com.earlyBuddy.earlybuddy_android.data.datasource.remote.retrofit


import com.earlyBuddy.earlybuddy_android.CookiesInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceImpl {
    private const val BASE_URL = "http://13.209.182.154:3456/"
    private const val BASE_URL_2 = "http://13.209.182.154:3458/"

    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(CookiesInterceptor())
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(CookiesInterceptor()).build()

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(
            okHttpClient
        )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val SERVICE: NetworkService = retrofit.create(
        NetworkService::class.java)

    private val retrofit2: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL_2).client(
            okHttpClient
        )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val SERVICE2: NetworkService = retrofit2.create(
        NetworkService::class.java)
}