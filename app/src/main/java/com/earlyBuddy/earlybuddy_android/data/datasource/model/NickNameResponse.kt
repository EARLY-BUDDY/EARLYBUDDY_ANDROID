package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class NickNameResponse(
    val status: Int,
    val message: String,
    val data: UserIdx?
)

data class UserIdx(
    val userIdx: Int,
    val beforeUserName: String?,
    val afterUserName: String
)