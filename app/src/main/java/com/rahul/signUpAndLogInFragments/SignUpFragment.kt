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
import com.rahul.viewModel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController
    private val viewModel: SignUpViewModel by viewModels()
    lateinit var pass: String
    lateinit var emaill: String
    lateinit var ph: String
    lateinit var name1: String
    lateinit var company1: String

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

        viewModel.password.observe(viewLifecycleOwner, Observer { password ->
            when {
                password.length < 5 -> {
                    binding.nextButton.isEnabled = false
                    binding.nextButton.isClickable = false

                    binding.editTextSetPassword.error = "minimum 5 characters"
                    Log.i("password", password)
                }
                password.isNullOrEmpty() -> {
                    binding.nextButton.isEnabled = false
                    binding.nextButton.isClickable = false

                    binding.editTextSetPassword.error = "Field is required"
                }
                else -> {
                    binding.nextButton.isClickable = true
                    binding.nextButton.isEnabled = true
                    //  pass = binding.editTextSetPassword.text.toString()
                    //  viewModel.validatePassword = true
                }
            }
        })

        viewModel.email.observe(viewLifecycleOwner, Observer { email ->
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.nextButton.isEnabled = false
                binding.nextButton.isClickable = false


                binding.editTextEmail.error = "Enter your valid email id"
            } else if (email.isNullOrEmpty()) {
                binding.nextButton.isEnabled = false
                binding.nextButton.isClickable = false


                binding.editTextEmail.error = "Field is required"
            } else {
                binding.nextButton.isClickable = true
                binding.nextButton.isEnabled = true

                // emaill = binding.editTextEmail.text.toString()
                //  viewModel.validateEmail = true

            }
        })

        viewModel.company.observe(viewLifecycleOwner, Observer { company ->
            when {
                company.isNullOrEmpty() -> {
                    binding.editTextCompanyName.error = "Field is required"
                    binding.nextButton.isEnabled = false
                    binding.nextButton.isClickable = false


                }
                company.length < 4 -> {
                    binding.nextButton.isEnabled = false
                    binding.nextButton.isClickable = false

                    binding.editTextCompanyName.error = "Minimum 4 characters"
                }
                else -> {
                    binding.nextButton.isClickable = true
                    binding.nextButton.isEnabled = true
                    //    company1 = binding.editTextCompanyName.text.toString()
                    //  viewModel.validateCompany = true
                    Log.i("company", viewModel.name.value.toString())

                }
            }
        })


        viewModel.phoneNumber.observe(viewLifecycleOwner, Observer { phoneNumber ->
            if (!Patterns.PHONE.matcher(phoneNumber.toString()).matches()) {
                binding.nextButton.isEnabled = false
                binding.nextButton.isClickable = false


                binding.editTextPhoneNumber.error = "Enter your valid phone number"
            } else if (phoneNumber == null) {
                binding.nextButton.isEnabled = false
                binding.nextButton.isClickable = false


                binding.editTextPhoneNumber.error = "This field is required"
            } else {
                binding.nextButton.isClickable = true
                binding.nextButton.isEnabled = true

                //    ph = binding.editTextPhoneNumber.text.toString()
                //   viewModel.validatePhoneNumber = true

            }
        })

        viewModel.name.observe(viewLifecycleOwner, Observer { name ->
            when {
                name.isNullOrEmpty() -> {
                    binding.nextButton.isEnabled = false
                    binding.nextButton.isClickable = false


                    binding.editTextYourName.error = "This field is required"
                }
                name.length > 15 -> {
                    binding.nextButton.isEnabled = false
                    binding.nextButton.isClickable = false

                    binding.editTextYourName.error = "Maximum 15 characters"

                }
                else -> {
                    binding.nextButton.isClickable = true
                    binding.nextButton.isEnabled = true

                }
            }

        })

        viewModel.signUp.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { message ->
                        Log.i("ServerResponse", message)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    }
                    val action = SignUpFragmentDirections.actionSignUpFragmentToBottomBarActivity()
                    navController.navigate(action)
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.error?.let { error ->
                        Log.i("ServerResponse", error)
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                    Log.i("ServerResponse", "Loading")

                }
            }
        })


        binding.apply {
            nextButton.setOnClickListener {
                binding.apply {

                    Log.i("button", "clicked")
                    editTextPhoneNumber.isEnabled = false
                    editTextSetPassword.isEnabled = false
                    editTextEmail.isEnabled = false
                    editTextCompanyName.isEnabled = false
                    editTextYourName.isEnabled = false


                    nextButton.isEnabled = false
                }
                val testNumber = editTextPhoneNumber.text.toString()
                val testName = binding.editTextYourName.text.toString()
                val testPassword = binding.editTextSetPassword.text.toString()
                val testCompany = binding.editTextCompanyName.text.toString()
                val testMail = binding.editTextEmail.text.toString()


                val signUpDetails =
                    SignUpData(testCompany, testMail, testNumber, testName, testPassword)
                viewModel.signUpData(signUpDetails)


            }
        }

        viewModel._isSignUp.observe(viewLifecycleOwner, Observer {
            binding.apply {
                editTextPhoneNumber.isEnabled = false
                editTextSetPassword.isEnabled = false
                editTextEmail.isEnabled = false
                editTextCompanyName.isEnabled = false
                editTextYourName.isEnabled = false


                nextButton.isEnabled = false
            }

        })

        viewModel.exception.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            binding.apply {

                editTextPhoneNumber.isEnabled = true
                editTextSetPassword.isEnabled = true
                editTextEmail.isEnabled = true
                editTextCompanyName.isEnabled = true
                editTextYourName.isEnabled = true


                nextButton.isEnabled = true
            }
        })




        return binding.root
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }


}