package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class CalendarResponse(
    val status: Int,
    val message: String,
    val data: CalendarData
)

data class CalendarData(
    val year: String,
    val month: String,
    val schedules :ArrayList<Schedule>
)

data class Schedule(
    val scheduleIdx: Int,
    val scheduleName : String,
    val scheduleStartTime: String,
    val endAddress: String
)

data class Date(
    var year : String,
    var month : String,
    var date : String,
    var dayOfWeek: Int,
    var isToDay: Boolean,
    var isCurrentMonth: Boolean,
    var isClickDay: Boolean,
    var schedule : ArrayList<Schedule> ? = null,
    var lines: Int
)