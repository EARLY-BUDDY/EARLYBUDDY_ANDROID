package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignInResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.google.gson.JsonObject
import io.reactivex.Observable

class SignInRepository(private val remoteDataSource: RemoteDataSource) {
    fun signIn(jsonObject : JsonObject) : Observable<SignInResponse> = remoteDataSource.signIn(jsonObject)
}