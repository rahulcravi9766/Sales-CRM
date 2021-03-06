package com.rahul.viewModel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.postRequest.MobileNumber
import com.rahul.postRequest.VerifyOtp
import com.rahul.repository.SalesRepository
import com.rahul.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val repository: SalesRepository,
    private val state: SavedStateHandle

) : ViewModel() {
//
//    init {
//        setValues()
//    }
    var otpLogIn: MutableLiveData<Resource<String>> = MutableLiveData()
    lateinit var mobileNumber : String
    var otp : MutableLiveData<Int> = MutableLiveData()
    var progress = ObservableField<Boolean>()
    var mainView = ObservableField(true)

    fun verifyOtp(otp: VerifyOtp) = viewModelScope.launch {

        try {
            otpLogIn.postValue(Resource.Loading())
            val response = repository.verifyOtp(otp)
            Log.i("responseOtp", response.toString())
            otpLogIn.postValue(handleResponse(response))

        } catch (error: Exception) {
            Log.i("responseOtp", error.toString())

        }
    }

    private fun handleResponse(response: Response<String>): Resource<String> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        } else {

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


//
//    private fun setValues() {
//        val otpDetails = state.get<MobileNumber>("mobileNumberDetails")
//        mobileNumber.value = otpDetails?.mobileNumber
//
//    }
}