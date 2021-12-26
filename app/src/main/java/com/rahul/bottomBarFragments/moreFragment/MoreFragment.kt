package com.rahul.bottomBarFragments.moreFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rahul.activities.BottomBarActivity
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentMoreBinding
import com.rahul.utils.toast

class MoreFragment : Fragment() {

    private lateinit var binding : FragmentMoreBinding
    private lateinit var navController: NavController
    private val bottomBarActivity = BottomBarActivity()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoreBinding.inflate(inflater, container, false)
        navController = findNavController()



        binding.addProductLayout.setOnClickListener {
            val action = MoreFragmentDirections.actionMoreFragmentToProductsListFragment()
            navController.navigate(action)
        }
        binding.inviteLayout.setOnClickListener {
            toast("hello invite")
            val action = MoreFragmentDirections.actionMoreFragmentToInviteTeammateFragment()
            navController.navigate(action)
        }

        return binding.root
    }


}