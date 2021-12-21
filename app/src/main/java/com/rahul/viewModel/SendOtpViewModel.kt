package com.rahul.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.postRequest.LogInData
import com.rahul.postRequest.MobileNumber
import com.rahul.repository.SalesRepository
import com.rahul.response.LogInResponse
import com.rahul.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class SendOtpViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {

    var mobileLogIn: MutableLiveData<Resource<String>> = MutableLiveData()
    var mobileNumber: MutableLiveData<String> = MutableLiveData()


    fun mobileNumberLogIn(mobileNumber: MobileNumber) = viewModelScope.launch {

        try {
            mobileLogIn.postValue(Resource.Loading())
            val response = repository.mobileNumber(mobileNumber)
            Log.i("responseN", response.toString())
            mobileLogIn.postValue(handleResponse(response))

        } catch (error: Exception) {

        }
    }

    private fun handleResponse(response: Response<String>): Resource<String> {
        Log.i("code","working")

        if (response.isSuccessful) {
            Log.i("code",response.code().toString())
            if (response.code() == 200) {
                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }
        } else {

            response.body()?.let { resultResponse ->
                return Resource.Error(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }




}