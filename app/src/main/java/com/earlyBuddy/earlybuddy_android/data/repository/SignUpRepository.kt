package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignUpResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl
import com.google.gson.JsonObject
import io.reactivex.Observable
import java.util.*

class SignUpRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()
    fun signUp(jsonObject: JsonObject) : Observable<SignUpResponse> = remoteDataSource.signUp(jsonObject)
}