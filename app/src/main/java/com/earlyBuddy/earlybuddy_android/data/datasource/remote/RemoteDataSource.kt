package com.earlyBuddy.earlybuddy_android.data.datasource.remote

import com.earlyBuddy.earlybuddy_android.data.datasource.model.*
import com.google.gson.JsonObject
import io.reactivex.Observable

interface RemoteDataSource {
    fun searchPlace(keyword: String, x: Double, y: Double): Observable<PlaceResponse>
    fun signUp(jsonObject: JsonObject): Observable<SignUpResponse>
    fun signIn(jsonObject: JsonObject): Observable<SignInResponse>
    fun searchRoute(
        SX: Double,
        SY: Double,
        EX: Double,
        EY: Double,
        SearchPathType: Int,
        scheduleStartTime: String
    ): Observable<SearchRouteResponse>

    fun scheduleDetail(
        scheduleIdx: Int
    ): Observable<ScheduleDetailResponse>
    fun postSchedule(jsonObject: JsonObject): Observable<DefaultResponse>
    fun deleteSchedule(scheduleIdx: Int): Observable<NoneDataResponse>

    fun homeSchedule(): Observable<HomeResponse>
    fun homeTestSchedule(scheduleCheck: Int): Observable<HomeResponse>


    fun getCalendarSchedules(year: String, month: String): Observable<CalendarResponse>

    fun registerFavoritePlaces(jsonObject: JsonObject): Observable<RegistFavoriteResponse>

    fun registerUserNickName(jsonObject: JsonObject): Observable<NickNameResponse>

    fun getFavoriteList(): Observable<GetFavoriteResponse>
}