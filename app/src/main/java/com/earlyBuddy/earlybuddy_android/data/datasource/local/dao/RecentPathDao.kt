package com.earlyBuddy.earlybuddy_android.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.earlyBuddy.earlybuddy_android.base.BaseDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPathEntity

@Dao
interface RecentPathDao : BaseDao<RecentPathEntity> {
    @Query("SELECT * FROM recentPath GROUP BY startPlaceName, endPlaceName ORDER BY id DESC")
    fun loadRecentPath(): LiveData<List<RecentPathEntity>>
}