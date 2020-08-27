package com.earlyBuddy.earlybuddy_android.data.datasource.model

import java.io.Serializable

data class Path(
    val pathType: Int,
    val totalTime: Int,
    val totalPay: Int,
    val transitCount: Int,
    val totalWalkTime: Int,
    val subPath: ArrayList<SubPath>,
    val leastTotalTime:Int
) : Serializable

data class SubPath(
    val trafficType: Int,
    val distance: Int,
    val sectionTime: Int,
    val stationCount: Int?,
    val lane: Lane?,
    val startName: String?,
    val startX: Double?,
    val startY: Double?,
    val endName: String?,
    val endX: Double?,
    val endY: Double?,
    val way: String?,
    val wayCode: Int?,
    val door: String?,
    val startExitNo: String?,
    val startExitX: Double?,
    val startExitY: Double?,
    val endExitNo: String?,
    val endExitX: Double?,
    val endExitY: Double?,
    val passStopList: ArrayList<String>,
    var clicked: Boolean?
) : Serializable

data class Lane(
    val name: String,
    val type: Int,
    val busID: Int
) : Serializable