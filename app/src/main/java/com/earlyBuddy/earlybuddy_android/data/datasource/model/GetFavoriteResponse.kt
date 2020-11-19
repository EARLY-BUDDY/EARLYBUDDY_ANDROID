package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class GetFavoriteResponse(
    val status: Int,
    val message: String,
    val data: ArrayList<Favorite>
)
