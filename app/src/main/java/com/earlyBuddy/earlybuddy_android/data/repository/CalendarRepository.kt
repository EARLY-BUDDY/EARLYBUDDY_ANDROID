package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource

class CalendarRepository(private val remoteDataSource: RemoteDataSource){
    fun getCalendarSchedules(year: String, month : String) = remoteDataSource.getCalendarSchedules(year, month)
}