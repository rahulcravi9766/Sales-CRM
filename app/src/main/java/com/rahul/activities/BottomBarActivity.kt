package com.rahul.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rahul.bottomBarFragments.*
import com.rahul.salescrm.R

class BottomBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_bar)

        val fragmentManager: FragmentManager = supportFragmentManager

        // define your fragments here
        val scheduleFragment: Fragment = SchedulesFragment()
        val dealsFragment: Fragment = DealsFragment()
        val activityFragment: Fragment = ActivityFragment()
        val leadsFragment : Fragment = LeadsFragment()
        val moreFragment : Fragment = MoreFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav_view)


        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.nav_schedule -> fragment = scheduleFragment
                R.id.nav_deals -> fragment = dealsFragment
                R.id.nav_activity -> fragment = activityFragment
                R.id.nav_leads -> fragment = leadsFragment
                R.id.nav_more -> fragment = moreFragment
            }
            fragmentManager.beginTransaction().replace(R.id.activity_bottom_container, fragment).commit()
            true
        }
        // Set default selection
        bottomNavigationView.selectedItemId = R.id.nav_schedule
    }
}