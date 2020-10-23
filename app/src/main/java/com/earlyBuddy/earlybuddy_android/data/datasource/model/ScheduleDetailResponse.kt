package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class ScheduleDetailResponse(
    val status: Int,
    val message: String,
    val data: ScheduleDetail
)

data class ScheduleDetail(
    val scheduleInfo: ScheduleInfo,
    val weekdayInfo: ArrayList<Int>,
    val noticeTime: ArrayList<NoticeTime>,
    val path: Path
)

data class ScheduleInfo(
    val scheduleIdx: Int,
    val scheduleName: String,
    val scheduleStartTime: String,
    val departTime : String,
    val startAddress: String,
    val startLatitude: Double,
    val startLongitude: Double,
    val endAddress: String,
    val endLongitude: Double,
    val endLatitude: Double,
    val noticeMin: Int,
    val noticeRange : Int,
    val arriveCount: Int,
    val scheduleStartDay: String
)


data class NoticeTime(
    val arriveTime: String,
    val noticeTime: String
)