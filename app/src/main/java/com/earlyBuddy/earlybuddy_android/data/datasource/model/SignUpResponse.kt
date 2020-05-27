package com.earlyBuddy.earlybuddy_android.data.datasource.model

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String
)