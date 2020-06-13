package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource

class SearchRouteRepository(private val remoteDataSource: RemoteDataSource) {
    fun getSearchRouteData(
        SX: Double,
        SY: Double,
        EX: Double,
        EY: Double,
        SearchPathType: Int
    ) = remoteDataSource.searchRoute(SX, SY, EX, EY, SearchPathType)
}