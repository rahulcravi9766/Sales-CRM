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
import androidx.navigation.NavController
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

            if (mobileNumber.length == 10){

                binding.inputMobileNumber.error = "Invalid Phone number"

            }

        })



        binding.sendOtpButton.setOnClickListener {
            mobileNumber = binding.phNoText.text.toString() + binding.inputMobileNumber.text.toString()
            Log.i("number",mobileNumber)
            val number = MobileNumber(mobileNumber)
            viewModel.mobileNumberLogIn(number)
        }

        viewModel.mobileLogIn.observe(this, Observer { response ->
            when (response) {

                is Resource.Success -> {

                    hideProgressBar()
                    showView()
                    response.data?.let { message ->
                        Log.i("OtpResponse", message)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    }
//
//                    val extras = FragmentNavigatorExtras(binding.popularMoviesRecycler to "detail_pic")
//                    val action: NavDirections =
//                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
//                    navController.navigate(action, extras)
                }

                is Resource.Error -> {
                    hideProgressBar()
                    showView()
                    response.error?.let { error ->
                        Log.i("OtpResponse", error)
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                    hideView()
                    Log.i("OtpResponse", "Loading")

                }
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