package com.earlyBuddy.earlybuddy_android.data.datasource.remote

import com.earlyBuddy.earlybuddy_android.data.datasource.model.*
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.retrofit.NetworkServiceImpl
import com.google.gson.JsonObject
import io.reactivex.Observable

class RemoteDataSourceImpl : RemoteDataSource {
    val api = NetworkServiceImpl.SERVICE
    val api2 = NetworkServiceImpl.SERVICE2

    override fun searchPlace(keyword: String, x:Double, y:Double): Observable<PlaceResponse> =
        api2.getPlaceData(keyword, x, y)
            .map {
                it }

    override fun signUp(jsonObject: JsonObject) : Observable<SignUpResponse> =
        api.postSignUpData(jsonObject)
            .map {
                it }

    override fun signIn(jsonObject: JsonObject): Observable<SignInResponse> =
        api.postSignInData(jsonObject)
            .map {
                it }

    override fun searchRoute(
        SX: Double,
        SY: Double,
        EX: Double,
        EY: Double,
        SearchPathType: Int,
        scheduleStartTime: String
    ): Observable<SearchRouteResponse> =
        api2.getSearchRouteData(SX, SY, EX, EY, SearchPathType,scheduleStartTime)
            .map {
                it
            }

    override fun scheduleDetail(scheduleIdx: Int): Observable<ScheduleDetailResponse> =
        api.getScheduleDetail(scheduleIdx)
            .map {
                it
            }

    override fun postSchedule(jsonObject: JsonObject): Observable<DefaultResponse> =
        api.postSchedule(jsonObject)
            .map {
                it
            }

    override fun homeSchedule(): Observable<HomeResponse> = api.getHomeSchedule()

    override fun getCalendarSchedules(year: String, month: String): Observable<CalendarResponse>
            = api.getCalendarSchedules(year, month)
        .map {
            it
        }

    override fun registerFavoritePlaces(jsonObject: JsonObject): Observable<RegistFavoriteResponse> =
        api.registerFavoritePlaces(jsonObject).map { it }

    override fun homeTestSchedule(scheduleCheck: Int): Observable<HomeResponse> =
        api.getHomeTestSchedule(scheduleCheck).map { it }

    override fun registerUserNickName(jsonObject: JsonObject): Observable<NickNameResponse> =
        api.registerUserNickName(jsonObject).map { it }

    override fun getFavoriteList(): Observable<GetFavoriteResponse> =
        api.getFavoriteList().map { it }

}