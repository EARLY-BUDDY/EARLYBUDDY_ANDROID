package com.earlyBuddy.earlybuddy_android.data.datasource.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: PostSignInData
)

data class PostSignInData(
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("userIdx")
    val idx: Int,
    @SerializedName("userName")
    val userName: String
)