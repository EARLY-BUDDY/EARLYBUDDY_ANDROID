package com.earlyBuddy.earlybuddy_android.data.datasource.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: List<PlaceSearch>
)

data class PlaceSearch(
    val placeName: String?,
    val addressName: String,
    val roadAddressName: String,
    val category : String,
    val distance : String,
    val x : Double,
    val y : Double
)