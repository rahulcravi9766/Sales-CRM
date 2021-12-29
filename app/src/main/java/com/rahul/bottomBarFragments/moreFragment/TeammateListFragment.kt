package com.rahul.bottomBarFragments.moreFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rahul.salescrm.databinding.FragmentTeammateListBinding


class TeammateListFragment : Fragment() {

    private lateinit var binding: FragmentTeammateListBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTeammateListBinding.inflate(inflater, container, false)
        navController = findNavController()





        return binding.root
    }

}