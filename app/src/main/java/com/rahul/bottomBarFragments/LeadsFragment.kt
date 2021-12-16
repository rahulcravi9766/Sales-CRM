package com.rahul.bottomBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentLeadsBinding


class LeadsFragment : Fragment() {

   private lateinit var binding : FragmentLeadsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLeadsBinding.inflate(inflater, container, false)




        return  binding.root
    }


}