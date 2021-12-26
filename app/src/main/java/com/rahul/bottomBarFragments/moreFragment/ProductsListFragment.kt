package com.rahul.bottomBarFragments.moreFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentInviteTeammateBinding
import com.rahul.salescrm.databinding.FragmentProductsListBinding

class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductsListBinding.inflate(inflater,container, false)
        navController = findNavController()



        return binding.root
    }


}