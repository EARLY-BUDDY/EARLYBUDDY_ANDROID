package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class Path(
    val pathType: Int,
    val totalTime: Int,
    val totalPay: Int,
    val transitCount: Int,
    val totalWalkTime: Int,
    val subPath: ArrayList<SubPath>,
    val leastTotalTime:Int
)

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
    val startID: Int?,
    val endID: Int?,
    val fastExitNo : Int,
    val fastExitX: Double?,
    val fastExitY: Double?,
    val passStopList: PassStopList?,
    var clicked: Boolean?
)

data class Lane(
    val name: String,
    val type: Int,
    val busID: Int
)

data class PassStopList(
    val stations: ArrayList<Station>
)

data class Station(
    val index: Int,
    val stationID: Int,
    val stationName: String,
    val x: Double,
    val y: Double
)

//val tempsubPath = arrayListOf<SubPath>(
//    SubPath(trafficType = 3,distance = 39,sectionTime = 1,stationCount = 3,lane = Lane(name = "경의중앙선",type = 104,busID = 0),startName = "구리",startX = 127.143749,startY = 37.603405,endName = "상봉",endX = ),SubPath()
//)
//)