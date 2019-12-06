package com.shibuyaxpress.petchaserkt.components.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.shibuyaxpress.petchaserkt.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btnLogOut.setOnClickListener {
            Log.d("Current User logged",FirebaseAuth.getInstance().currentUser.toString())
            FirebaseAuth.getInstance().signOut()
            finishAffinity()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))

        }
    }
}
