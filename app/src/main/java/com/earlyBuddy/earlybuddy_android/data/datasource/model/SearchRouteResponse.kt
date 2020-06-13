package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class SearchRouteResponse(
    val status: Int,
    val message: String,
    val data: Route
)

data class Route(
    val subwayCount: Int,
    val subwayBusCount: Int,
    val path: ArrayList<Path>
)