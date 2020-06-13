package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl

class PlaceListRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()
    fun searchPlace(keyword: String, x:Double, y:Double) = remoteDataSource.searchPlace(keyword, x, y)
}