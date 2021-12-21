package com.rahul.response


import com.google.gson.annotations.SerializedName


data class LogInResponse(
    @SerializedName("access")
    val accessToken: String,
    @SerializedName("refresh")
    val refreshToken: String
)