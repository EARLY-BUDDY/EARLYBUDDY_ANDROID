package com.earlyBuddy.earlybuddy_android.data.datasource.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val data: List<PlaceSearch>
)

data class PlaceSearch(
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("addressName")
    val addressName: String,
    @SerializedName("roadAddressName")
    val roadAddressName: String
)