package com.rahul.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rahul.bottomBarFragments.*
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.ActivityBottomBarBinding

class BottomBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomBarBinding
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityBottomBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavView.setupWithNavController(navController)

        //setting default selection
      //  binding.bottomNavView.selectedItemId = R.id.leadsFragment

    }
}