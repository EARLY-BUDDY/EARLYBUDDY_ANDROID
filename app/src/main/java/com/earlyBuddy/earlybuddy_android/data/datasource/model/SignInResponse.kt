package com.earlyBuddy.earlybuddy_android.data.datasource.model

data class SignInResponse(
    val status: Int,
    val message: String,
    val data: PostSignInData?
)

data class PostSignInData(
    val jwt: String,
    val userIdx: Int,
    val userName: String?
)