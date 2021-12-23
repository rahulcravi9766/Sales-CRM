package com.rahul.signUpAndLogInFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.rahul.postRequest.MobileNumber
import com.rahul.salescrm.databinding.FragmentSentOtpBinding
import com.rahul.utils.Resource
import com.rahul.viewModel.SendOtpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SentOtpFragment : Fragment() {

    private lateinit var binding: FragmentSentOtpBinding
    private val viewModel: SendOtpViewModel by viewModels()
    private lateinit var mobileNumber : String
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSentOtpBinding.inflate(inflater, container, false)

        binding.viewModelInXml = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()


        viewModel.mobileNumber.observe(viewLifecycleOwner, Observer { mobileNumber ->

            if (mobileNumber.length != 10){
                binding.inputMobileNumber.error = "Invalid Phone number"
            }
        })


        binding.sendOtpButton.setOnClickListener {

            mobileNumber = binding.phNoText.text.toString() + binding.inputMobileNumber.text.toString()
            Log.i("number",mobileNumber)
            val number = MobileNumber(mobileNumber)
            viewModel.mobileNumberLogIn(number)
//            val action = SentOtpFragmentDirections.actionSentOtpFragmentToVerifyOtpFragment()
//            navController.navigate(action)
        }

        viewModel.mobileLogIn.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.data?.let { message ->
                        Log.i("OtpResponse", message)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    }
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




        return binding.root
    }

}