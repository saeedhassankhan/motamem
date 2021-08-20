package org.matamem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import org.matamem.ui.home.HomeFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navBottomNavigation: BottomNavigationView;
    lateinit var fragmentContainer: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navBottomNavigation = findViewById(R.id.nav_bottom_navigation)

        fragmentContainer = findNavController(R.id.fragment_container)

        navBottomNavigation.setupWithNavController(fragmentContainer)
    }




    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.currentNavigationFragment

        if(currentFragment is HomeFragment){
            currentFragment.onBackPressed()
        }else{
            super.onBackPressed()

        }

    }


    private val FragmentManager.currentNavigationFragment: Fragment?
        get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()
}