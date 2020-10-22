package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.google.gson.JsonObject

class ScheduleRepository(private val remoteDataSource: RemoteDataSource) {

    fun getScheduleDetailData(scheduleIdx: Int) = remoteDataSource.scheduleDetail(scheduleIdx)
    fun postSchedule(jsonObject: JsonObject) = remoteDataSource.postSchedule(jsonObject)
}