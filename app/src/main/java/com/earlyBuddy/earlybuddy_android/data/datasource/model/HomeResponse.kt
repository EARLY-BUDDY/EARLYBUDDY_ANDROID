package com.earlyBuddy.earlybuddy_android.data.datasource.model

import java.io.Serializable

data class HomeResponse (
    val status : Int,
    val message :String,
    val data : HomeSchedule?
) : Serializable

data class HomeSchedule(
    val scheduleCheck:Int,
    val userName:String,
    val untilDepartCode:Int,
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
    val startName:String
)
data class ScheduleSummaryData(
    val pathType: Int,
    val totalTime: Int,
    val scheduleIdx: Int,
    val scheduleName: String,
    val scheduleStartTime: String,
    val endAddress: String,
    val startAddress: String
)