package com.rahul.network

import com.rahul.postRequest.SignUpData
import com.rahul.postRequest.Test
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SalesApi {

    //Here I will write the verbs for request and response from the server

    @POST("Registration")
    suspend fun signUpDetails(@Body signUpResponse : SignUpData) : Response<String>

}