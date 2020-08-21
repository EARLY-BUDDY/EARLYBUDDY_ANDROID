package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.google.gson.JsonObject

class InitialPlaceRepository (private val remoteDataSource: RemoteDataSource){

    fun registerFavoritePlaces(jsonObject: JsonObject) = remoteDataSource.registerFavoritePlaces(jsonObject)

}