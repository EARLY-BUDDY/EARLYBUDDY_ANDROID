package com.earlyBuddy.earlybuddy_android.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.earlyBuddy.earlybuddy_android.base.BaseDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity

@Dao
interface RecentPlaceDao : BaseDao<RecentPlaceEntity>{
    @Query("SELECT * FROM recentPlace")
    fun loadRecentPlace(): LiveData<List<RecentPlaceEntity>>

    override fun insert(obj: RecentPlaceEntity) : RecentPlaceEntity{
        return obj
    }
}
