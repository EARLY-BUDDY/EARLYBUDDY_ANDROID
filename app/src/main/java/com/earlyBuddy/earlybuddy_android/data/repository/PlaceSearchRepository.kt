package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource

class PlaceSearchRepository(private val remoteDataSource: RemoteDataSource) {

    fun searchPlace(keyword : String, x:Double, y:Double) = remoteDataSource.searchPlace(keyword, x, y)

//    val recentPlace: LiveData<List<RecentPlaceEntity>> = RecentPlaceDao().load


//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insert(recentPlace: RecentPlaceEntity) {
//        recentPlaceDao.insert(recentPlace)
//    }
}