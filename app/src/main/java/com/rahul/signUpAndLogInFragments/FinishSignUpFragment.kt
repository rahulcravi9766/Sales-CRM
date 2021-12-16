package com.rahul.signUpAndLogInFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rahul.salescrm.databinding.FragmentFinishSignUpBinding
import com.rahul.viewModel.FinishSignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinishSignUpFragment : Fragment() {


    private lateinit var binding: FragmentFinishSignUpBinding
    private lateinit var navController: NavController
    private val viewModel: FinishSignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.viewModelInXml = viewModel
        navController = findNavController()

        binding = FragmentFinishSignUpBinding.inflate(inflater, container, false)












        return binding.root
    }

}