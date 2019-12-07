package com.shibuyaxpress.petchaserkt.components.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.shibuyaxpress.petchaserkt.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btnLogOut.setOnClickListener {
            var gso = GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build()
            var googleSignInClient = GoogleSignIn.getClient(this,gso)
            googleSignInClient.signOut()
            AccessToken.setCurrentAccessToken(null)
            if (LoginManager.getInstance() != null){
                LoginManager.getInstance().logOut()
            }
            Log.d("Current User logged",FirebaseAuth.getInstance().currentUser.toString())
            FirebaseAuth.getInstance().signOut()
            finishAffinity()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))

        }
    }
}
