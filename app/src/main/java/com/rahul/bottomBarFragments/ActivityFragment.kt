package com.rahul.bottomBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentActivityBinding
import com.rahul.salescrm.databinding.FragmentFinishSignUpBinding

class ActivityFragment : Fragment() {


    private lateinit var binding : FragmentActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         binding =        FragmentActivityBinding.inflate(inflater, container, false)






        return binding.root

    }

}