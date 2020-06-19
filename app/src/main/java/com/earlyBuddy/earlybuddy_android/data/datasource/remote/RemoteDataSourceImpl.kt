package com.earlyBuddy.earlybuddy_android.data.datasource.remote

import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SearchRouteResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignInResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignUpResponse
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
        SearchPathType: Int
    ): Observable<SearchRouteResponse> =
        api2.getSearchRouteData(SX, SY, EX, EY, SearchPathType)
            .map {
                it
            }

}