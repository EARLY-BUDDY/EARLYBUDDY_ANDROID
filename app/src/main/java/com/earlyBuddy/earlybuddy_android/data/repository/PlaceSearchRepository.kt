package com.earlyBuddy.earlybuddy_android.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.database.RecentPlaceDB
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource

class PlaceSearchRepository(private val remoteDataSource: RemoteDataSource) {

    fun searchPlace(keyword : String, x:Double, y:Double) = remoteDataSource.searchPlace(keyword, x, y)

    fun getFavoritePlace() = remoteDataSource.getFavoriteList()

    private val database = RecentPlaceDB.getInstance(EarlyBuddyApplication.getGlobalApplicationContext())!!
    private val recentPlaceDao = database.recentPlaceDao()

    fun loadRecentPlace(): LiveData<List<RecentPlaceEntity>> {
        return recentPlaceDao.loadRecentPlace()
    }
    fun insert(recentPlace: RecentPlaceEntity) {
        InsertAsyncTask(recentPlaceDao).execute(recentPlace)
    }
    fun delete(recentPlace: RecentPlaceEntity){
        DeleteAsyncTask(recentPlaceDao).execute(recentPlace)
    }

    private class InsertAsyncTask<TE, TDO : BaseDao<TE>>(private val dao: TDO) : AsyncTask<TE, Void, Void>() {
        override fun doInBackground(vararg entity: TE): Void? {
            dao.insert(entity[0])
            return null
        }
    }
    private class DeleteAsyncTask<TE, TDO : BaseDao<TE>>(private val dao: TDO) : AsyncTask<TE, Void, Void>() {
        override fun doInBackground(vararg entity: TE): Void? {
            dao.delete(entity[0])
            return null
        }
    }
}