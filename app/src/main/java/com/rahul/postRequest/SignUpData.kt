package com.rahul.postRequest


import com.google.gson.annotations.SerializedName


data class SignUpData(
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)