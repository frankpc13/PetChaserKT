package com.shibuyaxpress.petchaserkt.components

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.models.User
import com.shibuyaxpress.petchaserkt.network.APIServiceGenerator
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnFacebookLogin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
        }
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        //check if user Logged In
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToMenu(currentUser)
        } else {
            Log.d("Login Activity","se tiene que logear")
        }
    }

    private fun goToMenu(user: FirebaseUser) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = APIServiceGenerator.petchaserClient.LoginUserAsync()
            }catch (e:Exception){

            }
        }
        startActivity(Intent(this@LoginActivity, MenuActivity::class.java).putExtra("userLogged",user as User))
    }
}
