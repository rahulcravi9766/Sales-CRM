package com.rahul.viewModel

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
    private val repository: SalesRepository
) : ViewModel() {

    var logIn: MutableLiveData<Resource<LogInResponse>> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var progress = ObservableField<Boolean>()
    var mainView = ObservableField(true)
    var emailBinding : String? = null
    var passwordBinding : String? = null


//    private var isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
//    val _isLoggedIn: LiveData<Boolean>
//        get() = isLoggedIn

    fun logInButton(view : View){

        viewModelScope.launch {

            try {
                if (emailBinding.isNullOrEmpty() || passwordBinding.isNullOrEmpty()){

                    Resource.Error("Please fill this",null)
                    Log.i("logInVM","failure")
                    return@launch
                }
                Log.i("logInVM","Success")
            }catch (error: Exception){
                Log.i("responseL", error.toString())
            }
        }


        //if everything is correct , we have to write the success code here
    }


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