package com.earlyBuddy.earlybuddy_android.data.datasource.remote.retrofit

import com.earlyBuddy.earlybuddy_android.data.datasource.model.*

import com.google.gson.JsonObject

import io.reactivex.Observable
import retrofit2.http.*

interface NetworkService {
    @GET("/searchAddress")
    fun getAdress(
        @Query("addr") addr: String
    ): Observable<HomeResponse>

    @GET("/searchAddress")
    fun getPlaceData(
        @Query("addr") addr: String,
        @Query("x") x: Double,
        @Query("y") y: Double
    ): Observable<PlaceResponse>

    @POST("/users/signup")
    fun postSignUpData(
        @Body body: JsonObject
    ): Observable<SignUpResponse>

    @POST("/users/signin")
    fun postSignInData(
        @Body body: JsonObject
    ): Observable<SignInResponse>

    @GET("/searchPath")
    fun getSearchRouteData(
        @Query("SX") SX: Double,
        @Query("SY") SY: Double,
        @Query("EX") EX: Double,
        @Query("EY") EY: Double,
        @Query("SearchPathType") SearchPathType: Int
    ): Observable<SearchRouteResponse>

    @GET("/schedules")
    fun getScheduleDetail(
        @Query("scheduleIdx") scheduleIdx: Int
    ): Observable<ScheduleDetailResponse>

    @GET("/home")
    fun getHomeSchedule(
    ): Observable<HomeResponse>

    @PUT("/users/setFavorite")
    fun registerFavoritePlaces(
        @Body body: JsonObject
    ): Observable<FavoriteResponse>
}