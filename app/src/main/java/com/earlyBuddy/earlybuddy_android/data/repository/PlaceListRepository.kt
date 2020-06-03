package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource

class PlaceListRepository(private val remoteDataSource: RemoteDataSource) {
    fun searchPlace(query: String) = remoteDataSource.searchPlace(query)
}