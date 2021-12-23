package com.rahul.viewModel

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.ObservableField
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
    val email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var phoneNumber = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var company = MutableLiveData<String>()
    var exception = MutableLiveData<String>()
    var progress = ObservableField<Boolean>()
    var buttonClick = ObservableField<Boolean>()
    var mainView = ObservableField(true)
    var isEmailValid: Boolean = false
    var isPasswordValid: Boolean = false
    var isPhValid: Boolean = false
    var isNameValid: Boolean = false
    var isCompanyValid: Boolean = false


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

    fun nextButtonClick() {
        Log.i("button1", "clicked")
        Log.i("button3", company.toString())

        if (isPasswordValid && isCompanyValid && isEmailValid && isNameValid && isPhValid) {
            Log.i("button2", "clicked")

            val signUp = SignUpData(
                company.toString(),
                email.toString(),
                phoneNumber.toString(),
                name.toString(),
                password.toString()
            )
             signUpData(signUp)
            this.buttonClick.set(true)
        }
    }

    fun progressBar(value: Boolean) {
        if (value) {
            this.progress.set(true)
        } else {
            this.progress.set(false)
        }
    }

    fun mainViewVisibility(value: Boolean) {
        if (value) {
            this.mainView.set(true)
        } else {
            this.mainView.set(false)
        }
    }


//    fun onNextButtonClick() {
//
//
//        _header.value = "rahul crm"
//
//    }


}