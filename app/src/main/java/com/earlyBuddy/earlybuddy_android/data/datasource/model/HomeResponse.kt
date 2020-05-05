package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class HomeResponse (
    val success : Boolean,
    val message :String,
    val data : HomeSchedule?
)

data class HomeSchedule(
    val ready :Boolean,
    val lastTransCount:Int,
    val arriveTime:String,
    val firstTrans:FirstTrans,
    val nextTransArriveTime:String,
    val scheduleSummaryData:ScheduleSummaryData
)

data class FirstTrans(
    val detailIdx:Int,
    val trafficType:Int,
    val subwayLane:Int?,
    val busNo:Int?,
    val busType:Int?,
    val detailStartAddress:String
)
data class ScheduleSummaryData(
    val scheduleIdx:Int,
    val scheduleName:String,
    val scheduleStartTime:String,
    val endAddress :String
)