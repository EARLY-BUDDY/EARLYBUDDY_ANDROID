package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class FavoriteResponse(
    val favoriteArr: ArrayList<Favorite>
)
data class GetFavoriteResponse(
    val status: Int,
    val message: String,
    val data: ArrayList<Favorite>
)

data class FavoriteData(
    val favoriteIdx: Int,
    val favoriteInfo: String,
    val favoriteCategory: Int,
    val favoriteLongitude: Double,
    val favoriteLatitude: Double
)