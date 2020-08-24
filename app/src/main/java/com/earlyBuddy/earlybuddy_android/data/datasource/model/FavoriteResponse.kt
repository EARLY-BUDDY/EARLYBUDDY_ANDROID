package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class FavoriteResponse(
    val status: Int,
    val message: String,
    val data: FavoriteData
)

data class FavoriteData(
    val userIdx: Int
)