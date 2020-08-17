package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class FavoriteResponse(
    val favoriteArr: ArrayList<Favorite>
)

data class Favorite(
    val favoriteInfo: String,
    val favoriteCategory: Int,
    val favoriteLongitude: Double,
    val favoriteLatitude: Double
)