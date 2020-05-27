package com.earlyBuddy.earlybuddy_android.data.datasource.remote

import android.annotation.SuppressLint
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignInResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignUpResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.retrofit.NetworkServiceImpl
import com.google.gson.JsonObject
import io.reactivex.Observable

class RemoteDataSourceImpl : RemoteDataSource {
    val api = NetworkServiceImpl.SERVICE

    override fun searchPlace(query: String): Observable<PlaceResponse> =
        api.getPlaceData(query)
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

}