package com.earlyBuddy.earlybuddy_android.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentPlace")
data class RecentPlaceEntity (
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "placeName") val placeName: String
)