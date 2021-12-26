package com.rahul.viewModel

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
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
    private val repository: SalesRepository,
    private val sharedPreferences: SharedPreferences

) : ViewModel() {


    private val isLoggedIn = false
    var logIn: MutableLiveData<Resource<LogInResponse>> = MutableLiveData()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var progress = ObservableField<Boolean>()
    var mainView = ObservableField(true)
    var isEmailValid = false
    var isPasswordValid = false




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

                    Log.i("responseAc", resultResponse.accessToken)
                    Log.i("responseRef", resultResponse.refreshToken)


                    return Resource.Success(resultResponse)
                }

        } else {

            response.body()?.let { resultResponse ->
                return Resource.Error(resultResponse.toString())

            }
        }

        return Resource.Error(response.message())
    }

    fun progressBar(value: Boolean) {
        if (value){
            this.progress.set(true)
        }else{
            this.progress.set(false)
        }
    }

    fun mainViewVisibility(value: Boolean){
        if (value){
            this.mainView.set(true)
        }else{
            this.mainView.set(false)
        }
    }
}