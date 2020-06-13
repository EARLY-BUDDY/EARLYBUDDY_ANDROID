package com.earlyBuddy.earlybuddy_android.data.datasource.remote

import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignInResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignUpResponse
import com.google.gson.JsonObject
import io.reactivex.Observable

interface RemoteDataSource {
    fun searchPlace(keyword: String, x:Double, y:Double) : Observable<PlaceResponse>
    fun signUp(jsonObject: JsonObject) : Observable<SignUpResponse>
    fun signIn(jsonObject: JsonObject) : Observable<SignInResponse>
}