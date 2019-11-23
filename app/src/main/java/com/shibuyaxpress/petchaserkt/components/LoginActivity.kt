package com.shibuyaxpress.petchaserkt.components

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shibuyaxpress.petchaserkt.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnFacebookLogin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
        }
    }
}
