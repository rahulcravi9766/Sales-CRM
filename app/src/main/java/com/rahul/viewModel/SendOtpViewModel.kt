package com.rahul.viewModel

import android.util.Log
import androidx.databinding.ObservableField
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
    var progress = ObservableField<Boolean>()
    var mainView = ObservableField(true)


    fun mobileNumberLogIn(mobileNumber: MobileNumber) = viewModelScope.launch {

        try {
            mobileLogIn.postValue(Resource.Loading())
            val response = repository.mobileNumber(mobileNumber)
            Log.i("res", response.toString())
            mobileLogIn.postValue(handleResponse(response))

        } catch (error: Exception) {

        }
    }

    private fun handleResponse(response: Response<String>): Resource<String> {
        Log.i("code","working")

        if (response.code() == 200) {
        if (response.isSuccessful) {
            Log.i("code",response.code().toString())

                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }
        } else {
            Log.i("code1",response.code().toString())
            Log.i("code2", response.errorBody().toString())

            response.body()?.let { resultResponse ->
                return Resource.Error(resultResponse)

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