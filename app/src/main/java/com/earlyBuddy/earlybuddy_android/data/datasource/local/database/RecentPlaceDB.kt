package com.earlyBuddy.earlybuddy_android.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.earlyBuddy.earlybuddy_android.data.datasource.local.dao.RecentPathDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.dao.RecentPlaceDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPathEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity

@Database(entities = [RecentPlaceEntity::class, RecentPathEntity::class], version = 2)
abstract class RecentPlaceDB : RoomDatabase() {
    abstract fun recentPlaceDao() : RecentPlaceDao
    abstract fun recentPathDao() : RecentPathDao

    companion object {

        private var instance: RecentPlaceDB? = null

        @Synchronized
        fun getInstance(context: Context): RecentPlaceDB? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    RecentPlaceDB::class.java, "RecentPlaceDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}