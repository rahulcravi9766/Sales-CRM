package com.rahul.network

import com.rahul.postRequest.*
import com.rahul.response.LogInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface SalesApi {

    //Here I will write the verbs for request and response from the server


    @POST("Registration")
    suspend fun signUpDetails(@Body signUpRequest: SignUpData): Response<String>

    @POST("login")
    suspend fun logInDetails(@Body logInRequest: LogInData): Response<LogInResponse>

    @POST("otplogin")
    suspend fun mobileLogIn(@Body mobileNumber: MobileNumber): Response<String>

    @POST("otpchecking")
    suspend fun verifyOtp(@Body otp: VerifyOtp): Response<String>

    @POST("invitation")
    suspend fun inviteTeammate(
        @Header("Authorization") token: String,
        @Body invitation: Invitation
    ): Response<String>
}