package com.earlyBuddy.earlybuddy_android.data.datasource.remote.retrofit

import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse

import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignInResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignUpResponse
import com.google.gson.JsonObject

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkService {
    @GET("/searchAddress")
    fun getAdress(
        @Query("addr") addr: String
    ): Observable<HomeResponse>

    @GET("/searchAddress")
    fun getPlaceData(
        @Query("addr") addr: String
    ): Observable<PlaceResponse>

    @POST("/users/signup")
    fun postSignUpData(
        @Body body: JsonObject
    ): Observable<SignUpResponse>

    @POST("/users/signin")
    fun postSignInData(
        @Body body: JsonObject
    ): Observable<SignInResponse>
}