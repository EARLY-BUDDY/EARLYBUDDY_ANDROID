package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.google.gson.JsonObject

class NickNameRepository(private val remoteDataSource: RemoteDataSource) {
    fun putUserNickName(jsonObject: JsonObject) = remoteDataSource.registerUserNickName(jsonObject)
}