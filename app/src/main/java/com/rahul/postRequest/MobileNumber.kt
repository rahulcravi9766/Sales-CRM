package com.rahul.postRequest


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class MobileNumber(
    @SerializedName("mobile_number")
    val mobileNumber: String
) : Parcelable