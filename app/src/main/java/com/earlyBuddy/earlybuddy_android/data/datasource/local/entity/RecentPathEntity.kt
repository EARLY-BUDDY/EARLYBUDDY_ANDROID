package com.earlyBuddy.earlybuddy_android.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentPath")
data class RecentPathEntity (
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    @ColumnInfo(name = "startPlaceName") val startPlaceName: String,
    @ColumnInfo(name = "endPlaceName") val endPlaceName: String,
    @ColumnInfo val sx: Double,
    @ColumnInfo val sy: Double,
    @ColumnInfo val ex: Double,
    @ColumnInfo val ey: Double
)