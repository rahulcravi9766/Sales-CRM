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
import com.rahul.viewModel.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {


    private lateinit var binding: FragmentLogInBinding
    private lateinit var navController: NavController
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREF_NAME = "myPref"
    private val KEY_PASSWORD = "password"
    private val KEY_EMAIL = "email"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sharedPreferences = context?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)!!

        val emailPreferences = sharedPreferences.getString(KEY_EMAIL,null)

        if (emailPreferences != null){
            val action = LogInFragmentDirections.actionLogInFragmentToBottomBarActivity()
            navController.navigate(action)
        }

        binding = FragmentLogInBinding.inflate(inflater, container, false)
        binding.viewModelInXml = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        navController = findNavController()



        binding.signUpText.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            navController.navigate(action)
        }

        binding.otpButton.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSentOtpFragment()
            navController.navigate(action)
        }


        binding.loginButton.setOnClickListener {

            email = binding.emailEditText.text.toString()
            password = binding.passwordEditText.text.toString()
            Log.i("data", email)
            Log.i("data", password)


            val logInDetails = LogInData(email, password)
            viewModel.logInData(logInDetails)


        }

        viewModel.logIn.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    showView()
                    response.data?.let { message ->
                        Log.i("LogInResponse", message.toString())
                        Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()

                    }
                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString(KEY_EMAIL,binding.emailEditText.text.toString())
                    editor.putString(KEY_PASSWORD,binding.passwordEditText.text.toString())
                    editor.apply()

                    val action = LogInFragmentDirections.actionLogInFragmentToBottomBarActivity()
                    navController.navigate(action)
                }

                is Resource.Error -> {
                    hideProgressBar()
                    showView()
                    response.error?.let { error ->
                        Log.i("LogInResponse", error)
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                    hideView()
                    Log.i("LogInResponse", "Loading")

                }
            }

        })


        viewModel.email.observe(this, Observer { email ->
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {


                binding.emailEditText.error = "Enter your valid email id"
            } else if (email.isNullOrEmpty()) {

                binding.emailEditText.error = "Field is required"
            } else {
                this.email = binding.emailEditText.text.toString()
            }

        })


        viewModel.password.observe(this, Observer { password ->

            if (!password.isNullOrEmpty()) {
                this.password = binding.passwordEditText.text.toString()
            }

        })




        return binding.root
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun hideView() {
        binding.mainView.visibility = View.INVISIBLE
    }

    private fun showView() {
        binding.mainView.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

}