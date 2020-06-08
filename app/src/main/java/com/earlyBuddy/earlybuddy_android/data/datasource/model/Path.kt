package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class RouteResponse(
    val status: Int,
    val message: String,
    val data: Route
)

data class Route(
    val subwayCount: Int,
    val subwayBusCount: Int,
    val path: ArrayList<Path>,
    val leastTransitCount:Int,
    val leastTotalWalkTime:Int
)

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
    val stationCount: Int,
    val lane: Lane,
    val startName: String,
    val startX: Double,
    val startY: Double,
    val endName: String,
    val endX: Double,
    val endY: Double,
    val way: String,
    val wayCode: Int,
    val door: String,
    val startID: Int,
    val endID: Int,
    val startExitX: Double,
    val startExitY: Double,
    val passStopList: PassStopList,
    val clicked: Boolean
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