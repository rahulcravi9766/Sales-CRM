package com.rahul.signUpAndLogInFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rahul.salescrm.databinding.FragmentLogInBinding
import com.rahul.viewModel.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {


    private lateinit var binding : FragmentLogInBinding
    private lateinit var navController: NavController
    private val viewModel : LogInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLogInBinding.inflate(inflater,container,false)
        binding.viewModelInXml = viewModel

        navController = findNavController()

        binding.signUpText.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            navController.navigate(action)
        }


        return binding.root
    }

}