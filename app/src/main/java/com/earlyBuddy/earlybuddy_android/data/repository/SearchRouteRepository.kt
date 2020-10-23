package com.earlyBuddy.earlybuddy_android.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.database.RecentPlaceDB
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPathEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource

class SearchRouteRepository(private val remoteDataSource: RemoteDataSource) {
    fun getSearchRouteData(
        SX: Double,
        SY: Double,
        EX: Double,
        EY: Double,
        SearchPathType: Int,
        scheduleStartTime : String
    ) = remoteDataSource.searchRoute(SX, SY, EX, EY, SearchPathType, scheduleStartTime)

    private val database = RecentPlaceDB.getInstance(EarlyBuddyApplication.getGlobalApplicationContext())!!
    private val recentPathDao = database.recentPathDao()

    fun loadRecentPath(): LiveData<List<RecentPathEntity>> {
        return recentPathDao.loadRecentPath()
    }

    fun insert(recentPath: RecentPathEntity) {
        InsertAsyncTask(recentPathDao).execute(recentPath)
    }

    fun delete(recentPath: RecentPathEntity){
        DeleteAsyncTask(recentPathDao).execute(recentPath)
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