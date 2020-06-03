package com.earlyBuddy.earlybuddy_android.data.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.dao.RecentPlaceDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.database.RecentPlaceDB
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity

class RecentPlaceRepository(private var recentPlaceDao: RecentPlaceDao) {


    private val context = EarlyBuddyApplication.getGlobalApplicationContext()

    init {
        val db = RecentPlaceDB.getInstance(context)!!
        recentPlaceDao = db.recentPlaceDao()
    }

    fun insert(entity: RecentPlaceEntity) {
        Log.e("repository insert 실행", recentPlaceDao.insert(RecentPlaceEntity(id = 1, placeName = "메롱")).id.toString())
        InsertAsyncTask(recentPlaceDao).execute(entity)
    }
    fun loadRecentPlace(): LiveData<List<RecentPlaceEntity>>? {
        Log.e("repository load 실행", recentPlaceDao.loadRecentPlace().value.toString())
        return recentPlaceDao.loadRecentPlace()
    }

    private class InsertAsyncTask<TE, TDO : BaseDao<TE>>(private val dao: TDO) : AsyncTask<TE, Void, Void>() {
        override fun doInBackground(vararg entity: TE): Void? {
            dao.insert(entity[0])
            return null
        }
    }
}