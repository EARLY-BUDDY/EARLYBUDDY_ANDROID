package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignUpResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.google.gson.JsonObject
import io.reactivex.Observable

class SignUpRepository(private val remoteDataSource: RemoteDataSource) {
    fun signUp(jsonObject: JsonObject) : Observable<SignUpResponse> = remoteDataSource.signUp(jsonObject)
}