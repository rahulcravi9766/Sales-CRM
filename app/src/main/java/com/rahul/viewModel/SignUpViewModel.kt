package com.rahul.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.rahul.postRequest.SignUpData
import com.rahul.repository.SalesRepository
import com.rahul.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {
    lateinit var navController: NavController
    var signUp: MutableLiveData<Resource<String>> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var phoneNumber: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var company: MutableLiveData<String> = MutableLiveData()
    val exception = MutableLiveData<String>()


    var isAllValidated: MutableLiveData<Boolean> = MutableLiveData()

    private var isSignUp: MutableLiveData<Boolean> = MutableLiveData()
    val _isSignUp: LiveData<Boolean>
        get() = isSignUp


    private val _header = MutableLiveData("Sales CRM")
    private val _nameFromXml: MutableLiveData<String> = MutableLiveData()
    val nameFromXml: LiveData<String> = _nameFromXml


    //setting the text for xml from here
    val header: LiveData<String> = _header



    fun signUpData(signUpData: SignUpData) = viewModelScope.launch {

        try {
            signUp.postValue(Resource.Loading())
            val response = repository.signUpDetails(signUpData)
            Log.i("response", response.toString())
            signUp.postValue(handleResponse(response))


        } catch (error: Exception) {
            exception.value = error.toString()
            Log.i("errorMessage", exception.value.toString())
        }
    }


    private fun handleResponse(response: Response<String>): Resource<String> {

        if (response.isSuccessful) {
            isSignUp.value = true
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        } else {

            response.body()?.let { resultResponse ->
                isSignUp.value = false
                return Resource.Error(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }




//    fun onNextButtonClick() {
//
//
//        _header.value = "rahul crm"
//
//    }


}