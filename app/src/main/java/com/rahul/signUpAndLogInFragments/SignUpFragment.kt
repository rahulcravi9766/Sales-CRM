package com.rahul.signUpAndLogInFragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rahul.postRequest.SignUpData
import com.rahul.salescrm.databinding.FragmentSignUpBinding
import com.rahul.utils.Resource
import com.rahul.utils.toast
import com.rahul.viewModel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController
    private val viewModel: SignUpViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.viewModelInXml = viewModel
        navController = findNavController()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.logInText.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLogInFragment()
            navController.navigate(action)
        }

        viewModel.password.observe(viewLifecycleOwner, Observer {
            if (it.length < 5) {
                binding.editTextSetPassword.error = "minimum 5 characters"
                return@Observer
            }
            if (it.isNullOrEmpty()) {
                binding.editTextSetPassword.error = "Field is required"
                return@Observer
            }
            viewModel.isPasswordValid = true
            Log.i("password", viewModel.password.value.toString())


        })

        viewModel.email.observe(viewLifecycleOwner, Observer {
            if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                binding.editTextEmail.error = "Enter your valid email id"
                return@Observer
            }
            if (it.isNullOrEmpty()) {
                binding.editTextEmail.error = "Field is required"
                return@Observer
            }
            viewModel.isEmailValid = true
            Log.i("email", viewModel.email.value.toString())


        })

        viewModel.company.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                binding.editTextCompanyName.error = "Field is required"
            //    binding.companyEditContainer.helperText = "Field is required"
                return@Observer
            }
            if (it.length < 4) {
                binding.editTextCompanyName.error = "Minimum 4 characters"
              //  binding.companyEditContainer.helperText = "Minimum 4 characters"
                return@Observer
            }

            viewModel.isCompanyValid = true
            Log.i("company", viewModel.company.value.toString())

        })

        viewModel.name.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                binding.editTextYourName.error = "This field is required"
                return@Observer
            }

            if (it.length < 4) {
                binding.editTextYourName.error = "Minimum 4 characters"
                return@Observer
            }
            viewModel.isNameValid = true
            Log.i("name", viewModel.name.value.toString())

        })


        viewModel.phoneNumber.observe(viewLifecycleOwner, Observer {
            if (!it.matches(".*[0-9]".toRegex())) {
                binding.editTextPhoneNumber.error = "Enter your valid phone number"
                return@Observer

            }
            if (it.isNullOrEmpty()) {
                binding.editTextPhoneNumber.error = "This field is required"
                return@Observer
            }
            if (it.length != 10) {
                binding.editTextPhoneNumber.error = "Must be 10 Digits"
                return@Observer
            }
            viewModel.isPhValid = true
            Log.i("phone", viewModel.phoneNumber.value.toString())

        })

        viewModel.signUp.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.data?.let { message ->
                        Log.i("ServerResponse", message)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    val action = SignUpFragmentDirections.actionSignUpFragmentToBottomBarActivity()
                    navController.navigate(action)
                }

                is Resource.Error -> {
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.error?.let { error ->
                        Log.i("ServerResponse", error)
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                    }
                }

                is Resource.Loading -> {
                    viewModel.progressBar(true)
                    viewModel.mainViewVisibility(false)
                    Log.i("ServerResponse", "Loading")

                }
            }
        })

            binding.nextButton.setOnClickListener {
                Log.i("if", "button clicked")
                if (viewModel.isCompanyValid && viewModel.isPasswordValid && viewModel.isEmailValid &&
                    viewModel.isNameValid && viewModel.isPhValid) {
                    Log.i("if", "working")
                val signUpDetails =
                    SignUpData(
                        viewModel.company.value!!,
                        viewModel.email.value!!,
                        viewModel.phoneNumber.value!!,
                        viewModel.name.value!!,
                        viewModel.password.value!!
                    )
                viewModel.signUpData(signUpDetails)
            }else{
                toast("Enter correct details")
                }

       }

        return binding.root
    }
}