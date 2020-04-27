package com.earlyBuddy.earlybuddy_android.data.datasource.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("success")
    val success: String,
    @SerializedName("message")
    val data: ArrayList<PlaceSearch>
)

data class PlaceSearch(
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("addressName")
    val addressName: String,
    @SerializedName("roadAddressName")
    val roadAddressName: String
)