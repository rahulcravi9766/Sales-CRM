package com.rahul.postRequest


import com.google.gson.annotations.SerializedName



data class LogInData(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)