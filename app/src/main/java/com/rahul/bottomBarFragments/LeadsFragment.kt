package com.rahul.bottomBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahul.adapter.ListOfLeadsAdapter
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentLeadsBinding
import com.rahul.utils.toast


class LeadsFragment : Fragment() {

    private lateinit var binding: FragmentLeadsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLeadsBinding.inflate(inflater, container, false)

        val leadsAdapter = ListOfLeadsAdapter()


        binding.apply {
            leadNameRv.apply {
                adapter = leadsAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }

        binding.leadNameRv.setOnClickListener {
            toast("clicked")
        }
        return binding.root
    }


}