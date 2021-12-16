package com.rahul.repository

import com.rahul.network.SalesApi
import com.rahul.postRequest.SignUpData
import retrofit2.Response
import javax.inject.Inject

class SalesRepository @Inject constructor(
    private val api: SalesApi
) {
    suspend fun signUpDetails(signUpData: SignUpData): Response<String> {
        return api.signUpDetails(signUpData)

    }
}