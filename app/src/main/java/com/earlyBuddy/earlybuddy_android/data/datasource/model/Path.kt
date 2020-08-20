package com.earlyBuddy.earlybuddy_android.data.datasource.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
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
    val fastExitNo : Int,
    val fastExitX: Double?,
    val fastExitY: Double?,
    val passStopList: ArrayList<String>,
    var clicked: Boolean?
) : Serializable

data class Lane(
    val name: String,
    val type: Int,
    val busID: Int
) : Serializable