package com.rahul.postRequest


import com.google.gson.annotations.SerializedName

data class Invitation(
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val role: String
)