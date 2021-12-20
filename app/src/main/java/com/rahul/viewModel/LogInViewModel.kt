package com.rahul.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.postRequest.LogInData
import com.rahul.repository.SalesRepository
import com.rahul.response.LogInResponse
import com.rahul.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {

    var logIn: MutableLiveData<Resource<LogInResponse>> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

//    private var isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
//    val _isLoggedIn: LiveData<Boolean>
//        get() = isLoggedIn


    fun logInData(logInData: LogInData) = viewModelScope.launch {


        try {
            logIn.postValue(Resource.Loading())
            val response = repository.logInDetails(logInData)
            Log.i("response", response.toString())
            logIn.postValue(handleResponse(response))

        } catch (error: Exception) {
            Log.i("responseL", error.toString())

        }
    }

    private fun handleResponse(response: Response<LogInResponse>): Resource<LogInResponse> {


            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }

        } else {

            response.body()?.let { resultResponse ->
                return Resource.Error(resultResponse.toString())

            }
        }

        return Resource.Error(response.message())
    }
}