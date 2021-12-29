package com.rahul.signUpAndLogInFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rahul.postRequest.VerifyOtp
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentSignUpBinding
import com.rahul.salescrm.databinding.FragmentVerifyOtpBinding
import com.rahul.utils.Resource
import com.rahul.viewModel.SignUpViewModel
import com.rahul.viewModel.VerifyOtpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyOtpFragment : Fragment() {

    private lateinit var binding: FragmentVerifyOtpBinding
    private lateinit var navController: NavController
    private val viewModel: VerifyOtpViewModel by viewModels()
    private val args : VerifyOtpFragmentArgs by navArgs()
    private var otp : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentVerifyOtpBinding.inflate(inflater, container, false)
        binding.viewModelInXml = viewModel
        navController = findNavController()

        viewModel.mobileNumber = args.phNumber




        binding.verifyOtpButton.setOnClickListener {


        }


        viewModel.otp.observe(viewLifecycleOwner, Observer { otp ->
            if(otp == null){
                binding.otpEditText.error = "Invalid Otp"
            }

        })

        viewModel.otpLogIn.observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is Resource.Success -> {
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.data?.let { message ->
                        Log.i("OtpResponse", message)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    }

//                    val action = LogInFragmentDirections.actionLogInFragmentToBottomBarActivity()
//                    navController.navigate(action)
                }

                is Resource.Error -> {
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.error?.let { error ->
                        Log.i("OtpResponse", error)
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                    }
                }

                is Resource.Loading -> {
                    viewModel.progressBar(true)
                    viewModel.mainViewVisibility(false)
                    Log.i("OtpResponse", "Loading")

                }
            }
        })

        binding.verifyOtpButton.setOnClickListener {

            val mobileNumber = viewModel.mobileNumber
           val verify = VerifyOtp(mobileNumber,otp)
            Log.i("OtpValues", verify.toString())

            viewModel.verifyOtp(verify)
        }


        return binding.root
    }
}