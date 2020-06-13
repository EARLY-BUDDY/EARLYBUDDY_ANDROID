package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.model.PostSignInData
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignInResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl
import com.google.gson.JsonObject
import io.reactivex.Observable
import java.util.*

class SignInRepository{
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()
    fun signIn(jsonObject : JsonObject) : Observable<SignInResponse> = remoteDataSource.signIn(jsonObject)
}