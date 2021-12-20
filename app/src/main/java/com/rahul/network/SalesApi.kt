package com.rahul.network

import com.rahul.postRequest.LogInData
import com.rahul.postRequest.MobileNumber
import com.rahul.postRequest.VerifyOtp
import com.rahul.postRequest.SignUpData
import com.rahul.response.LogInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SalesApi {

    //Here I will write the verbs for request and response from the server

    @POST("Registration")
    suspend fun signUpDetails(@Body signUpRequest : SignUpData) : Response<String>

    @POST("login")
    suspend fun logInDetails(@Body logInRequest : LogInData) : Response<LogInResponse>

    @POST("otplogin")
    suspend fun mobileLogIn(@Body mobileNumber : MobileNumber) : Response<String>

    @POST("otpchecking")
    suspend fun verifyOtp(@Body otp : VerifyOtp) : Response<String>
}