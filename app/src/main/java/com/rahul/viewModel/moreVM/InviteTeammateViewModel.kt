package com.rahul.viewModel.moreVM

import android.content.SharedPreferences
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.postRequest.Invitation
import com.rahul.postRequest.SignUpData
import com.rahul.repository.SalesRepository
import com.rahul.salescrm.R
import com.rahul.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class InviteTeammateViewModel @Inject constructor(
    private val repository: SalesRepository,
    private val sharedPreferences: SharedPreferences

) : ViewModel() {

    var email = MutableLiveData<String>()
    var isEmailValid = false
    var exception = MutableLiveData<String>()
    var invite: MutableLiveData<Resource<String>> = MutableLiveData()
    var progress = ObservableField<Boolean>()
    var mainView = ObservableField(true)



    fun inviteTeammate(invitation: Invitation) = viewModelScope.launch {

        try {
            invite.postValue(Resource.Loading())
            val token = sharedPreferences.getString("token","").toString()
            //todo check token with log
            val response = repository.inviteTeammate(token,invitation)
            Log.i("response", response.toString())
            invite.postValue(handleResponse(response))


        } catch (error: Exception) {
            exception.value = error.toString()
            Log.i("errorMessage", exception.value.toString())
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

}