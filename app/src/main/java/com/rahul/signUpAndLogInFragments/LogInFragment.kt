package com.rahul.signUpAndLogInFragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rahul.postRequest.LogInData
import com.rahul.salescrm.databinding.FragmentLogInBinding
import com.rahul.utils.Resource
import com.rahul.utils.toast
import com.rahul.viewModel.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class LogInFragment : Fragment() {


    private lateinit var binding: FragmentLogInBinding
    private lateinit var navController: NavController
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
   // private val SHARED_PREF_NAME = "myPref"
    private val KEY_PASSWORD = "password"
    private val KEY_EMAIL = "email"
    private val KEY_REFRESH = "refresh"
    private val KEY_ACCESS = "access"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        navController = findNavController()

        viewModel.isValidated.observe(viewLifecycleOwner, Observer {
            if (it){
                val action = LogInFragmentDirections.actionLogInFragmentToBottomBarActivity()
                navController.navigate(action)
            }
        })
      //  sharedPreferences = context?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)!!

//        val accessToken = sharedPreferences.getString("token",null)
//
//        if (accessToken != null){
//            val action = LogInFragmentDirections.actionLogInFragmentToBottomBarActivity()
//            navController.navigate(action)
//        }


//        GlobalScope.launch(Dispatchers.IO) {
//            delay(1000)
//        }
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        binding.viewModelInXml = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        binding.signUpText.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            navController.navigate(action)
        }

        binding.otpButton.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSentOtpFragment()
            navController.navigate(action)
        }

        viewModel.logIn.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i("refresh",response.data!!.accessToken)
//                    val refreshToken = response.data.refreshToken
//                    val accessToken = response.data.accessToken
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.data.let { message ->
                        Log.i("LogInResponse", message.toString())
                         toast("Log In successful")
                    }
                    viewModel.saveUserInfo(response.data.refreshToken)



//                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
//                    editor.putString(KEY_EMAIL,binding.emailEditText.text.toString())
//                    editor.putString(KEY_PASSWORD,binding.passwordEditText.text.toString())
//                    editor.putString(KEY_REFRESH,response.data.refreshToken)
//                    editor.putString(KEY_ACCESS,response.data.accessToken)
//                    editor.apply()
//                     val prefsAIDs = sharedPreferences.all.values
//                    Log.i("shared",prefsAIDs.toString())
//
//                    val token = sharedPreferences.getString(KEY_REFRESH,"").toString()
//                    Log.i("refToken",token)

                    val action = LogInFragmentDirections.actionLogInFragmentToBottomBarActivity()
                    navController.navigate(action)

//                    activity?.run {
//                        supportFragmentManager.beginTransaction().remove(this@LogInFragment)
//                            .commitAllowingStateLoss()
//                    }
                }

                is Resource.Error -> {
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.error?.let { error ->
                        Log.i("LogInResponse", error)
                        toast(error)
                    }
                }

                is Resource.Loading -> {
                    viewModel.progressBar(true)
                    viewModel.mainViewVisibility(false)
                    Log.i("LogInResponse", "Loading")
                }
            }
        })

        viewModel.email.observe(this, Observer { email ->
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEditText.error = "Invalid Email Id"
                return@Observer
            }
            if (email.isNullOrEmpty()) {
                binding.emailEditText.error = "Field is required"
                return@Observer
            }
                viewModel.isEmailValid = true
            Log.i("email", viewModel.email.value.toString())

        })

        viewModel.password.observe(this, Observer { password ->
            if (password.isNullOrEmpty()) {
                binding.passwordEditText.error = "Field is required"
                return@Observer
            }
            viewModel.isPasswordValid = true
            Log.i("password", viewModel.password.value.toString())

        })

        binding.loginButton.setOnClickListener {
            if (viewModel.isEmailValid && viewModel.isPasswordValid){
                val logInDetails = LogInData(viewModel.email.value!!, viewModel.password.value!!)
                viewModel.logInData(logInDetails)
            }else{
                toast("Invalid Email Id or Password")
            }
        }
        return binding.root
    }
}

//todo check the back stack working of log in