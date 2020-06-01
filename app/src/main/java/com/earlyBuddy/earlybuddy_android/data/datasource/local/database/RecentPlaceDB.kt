package com.earlyBuddy.earlybuddy_android.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.earlyBuddy.earlybuddy_android.data.datasource.local.dao.RecentPlaceDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [RecentPlaceEntity::class], version = 1)
abstract class RecentPlaceDB : RoomDatabase() {
    abstract fun recentPlaceDao() : RecentPlaceDao

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