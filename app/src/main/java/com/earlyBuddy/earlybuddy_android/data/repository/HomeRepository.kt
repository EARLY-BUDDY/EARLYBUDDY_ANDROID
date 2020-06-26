package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource

class HomeRepository(private val remoteDataSource: RemoteDataSource) {
    fun getHomeData() = remoteDataSource.homeSchedule()
}