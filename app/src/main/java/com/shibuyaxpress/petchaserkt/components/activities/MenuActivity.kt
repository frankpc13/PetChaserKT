package com.shibuyaxpress.petchaserkt.components.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.components.fragments.HomeFragment
import com.shibuyaxpress.petchaserkt.components.fragments.LocationFragment
import com.shibuyaxpress.petchaserkt.components.fragments.ReportFragment
import com.shibuyaxpress.petchaserkt.components.fragments.RewardFragment
import com.shibuyaxpress.petchaserkt.modules.GlideApp
import com.shibuyaxpress.petchaserkt.utils.UserPreferences
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var navController:NavController
    private lateinit var imageProfile: CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        toolbar  = findViewById(R.id.toolbar)
        val bottomNavigationBar: BottomNavigationView = findViewById(R.id.navigation)
        imageProfile = findViewById(R.id.imageProfile)
        bottomNavigationBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        openFragment(HomeFragment.newInstance())
        textBar.text = "Inicio"

        imageProfile.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ProfileActivity::class.java))
        }
        setUserData()

        //navigation style
        //getting the navigation controller
        //navController = Navigation.findNavController(this,R.id.fragment)
        //setting the navigation controller to bottom nav
        //bottomNavigationBar.setupWithNavController(navController)
        //setting up the action bar
        //NavigationUI.setupActionBarWithNavController(this,navController)
    }

   /* override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
*/
    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home-> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    private fun setUserData() {
            GlideApp.with(this).load(UserPreferences.currentUserLogged!!.image).into(imageProfile)
    }

    private val navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item ->
        when(item.itemId){
            R.id.navigation_home -> {
                textBar.text = "Inicio"
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_alerts -> {
                textBar.text = "Reportes"
                val reportFragment = ReportFragment.newInstance()
                openFragment(reportFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_near_me -> {
                textBar.text = "Cerca de mi"
                val locationFragment = LocationFragment.newInstance()
                openFragment(locationFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_rewards -> {
                textBar.text = "Recompensas"
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
