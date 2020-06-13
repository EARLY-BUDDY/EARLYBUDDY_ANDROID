package com.earlyBuddy.earlybuddy_android.data.datasource.remote

import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SearchRouteResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignInResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignUpResponse
import com.google.gson.JsonObject
import io.reactivex.Observable

interface RemoteDataSource {
    fun searchPlace(query: String) : Observable<PlaceResponse>
    fun signUp(jsonObject: JsonObject) : Observable<SignUpResponse>
    fun signIn(jsonObject: JsonObject) : Observable<SignInResponse>
    fun searchRoute(
        SX: Double,
        SY: Double,
        EX: Double,
        EY: Double,
        SearchPathType: Int
    ): Observable<SearchRouteResponse>
}