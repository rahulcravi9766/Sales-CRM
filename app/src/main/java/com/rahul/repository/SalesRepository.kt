package com.rahul.repository

import android.util.Log
import com.rahul.network.SalesApi
import com.rahul.postRequest.*
import com.rahul.response.LogInResponse
import retrofit2.Response
import javax.inject.Inject

class SalesRepository @Inject constructor(
    private val api: SalesApi
) {
    suspend fun signUpDetails(signUpData: SignUpData): Response<String> {
        return api.signUpDetails(signUpData)

    }

    suspend fun logInDetails(logInData: LogInData): Response<LogInResponse>{
        return api.logInDetails(logInData)

    }

    suspend fun mobileNumber(mobileNumber: MobileNumber): Response<String> {
        return api.mobileLogIn(mobileNumber)

    }

    suspend fun verifyOtp(otp : VerifyOtp) : Response<String>{
        return api.verifyOtp(otp)
    }

    suspend fun inviteTeammate(token: String,invitation: Invitation): Response<String>{
        return api.inviteTeammate(token,invitation)
    }
}