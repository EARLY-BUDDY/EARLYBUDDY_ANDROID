package com.earlyBuddy.earlybuddy_android.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.earlyBuddy.earlybuddy_android.data.datasource.local.dao.RecentPlaceDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl

class PlaceSearchRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    fun searchPlace(keyword : String, x:Double, y:Double) = remoteDataSource.searchPlace(keyword, x, y)

//    val recentPlace: LiveData<List<RecentPlaceEntity>> = RecentPlaceDao().load


//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insert(recentPlace: RecentPlaceEntity) {
//        recentPlaceDao.insert(recentPlace)
//    }
}