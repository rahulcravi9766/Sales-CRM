package com.rahul.postRequest


import com.google.gson.annotations.SerializedName


data class VerifyOtp(
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("otp")
    val otp: Int
)