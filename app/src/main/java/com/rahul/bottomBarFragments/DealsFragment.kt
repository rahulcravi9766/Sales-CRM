package com.rahul.bottomBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentDealsBinding

class DealsFragment : Fragment() {

    private lateinit var binding : FragmentDealsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDealsBinding.inflate(inflater, container, false)





        return binding.root
    }


}