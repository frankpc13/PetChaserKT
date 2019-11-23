package com.shibuyaxpress.petchaserkt.components

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.components.fragments.HomeFragment
import com.shibuyaxpress.petchaserkt.components.fragments.LocationFragment
import com.shibuyaxpress.petchaserkt.components.fragments.ReportFragment
import com.shibuyaxpress.petchaserkt.components.fragments.RewardFragment

class MenuActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        toolbar  = findViewById(R.id.toolbar)
        val bottomNavigationBar: BottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    private val navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item ->
        when(item.itemId){
            R.id.navigation_home -> {
                toolbar.title = "Inicio"
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_alerts -> {
                toolbar.title = "Reportes"
                val reportFragment = ReportFragment.newInstance()
                openFragment(reportFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_near_me -> {
                toolbar.title = "Cerca de mi"
                val locationFragment = LocationFragment.newInstance()
                openFragment(locationFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_rewards -> {
                toolbar.title = "Recompensas"
                val rewardFragment = RewardFragment.newInstance()
                openFragment(rewardFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment:Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
