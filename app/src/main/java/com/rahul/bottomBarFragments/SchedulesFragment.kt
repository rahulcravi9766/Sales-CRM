package com.rahul.bottomBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentShedulesBinding

class SchedulesFragment : Fragment() {

   private lateinit var binding : FragmentShedulesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentShedulesBinding.inflate(inflater, container, false)





        return binding.root
    }


}