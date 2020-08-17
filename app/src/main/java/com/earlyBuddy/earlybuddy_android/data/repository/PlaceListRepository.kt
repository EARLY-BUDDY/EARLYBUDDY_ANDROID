package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource

class PlaceListRepository(private val remoteDataSource: RemoteDataSource) {
    fun searchPlace(keyword: String, x:Double, y:Double) = remoteDataSource.searchPlace(keyword, x, y)
}