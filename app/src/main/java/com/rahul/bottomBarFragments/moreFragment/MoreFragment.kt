package com.rahul.bottomBarFragments.moreFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    private lateinit var binding : FragmentMoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoreBinding.inflate(inflater, container, false)




        return binding.root
    }


}